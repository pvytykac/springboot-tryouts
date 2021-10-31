package net.pvytykac.db.impl;

import net.pvytykac.db.Entity;
import net.pvytykac.db.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class MapRepositoryImpl<ID, ENTITY extends Entity<ID>> implements Repository<ID, ENTITY> {

    private final ConcurrentMap<ID, ENTITY> map = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    @Override
    public long count() {
        return (long) lockAndPerform(ConcurrentMap::size);
    }

    @Override
    public long countMatching(Query<ENTITY> query) {
        return lockAndPerform(map -> map.values().stream()
                .filter(query)
                .count());
    }

    @Override
    public Collection<ENTITY> listAll() {
        return lockAndPerform(ConcurrentMap::values);
    }

    @Override
    public Collection<ENTITY> listMatching(Query<ENTITY> query) {
        return lockAndPerform(map -> map.values().stream()
                .filter(query)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<ENTITY> findById(ID id) {
        return lockAndPerform(map -> Optional.ofNullable(map.get(id)));
    }

    @Override
    public void insert(ENTITY entity) throws DuplicateKeyException {
        lockAndPerform(map -> {
            ENTITY previous = map.putIfAbsent(entity.getId(), entity);
            if (previous != null) {
                throw new DuplicateKeyException(entity.getId());
            }

            return null;
        });
    }

    @Override
    public boolean updateById(ID id, Update<ENTITY> update) {
        return lockAndPerform(map -> {
            Optional<ENTITY> current = findById(id);
            boolean exists = current.isPresent();

            if (exists) {
                ENTITY updated = update.apply(current.get());

                if (updated == null) {
                    throw new RuntimeException("The result of this update is null, use the delete method instead.");
                }

                map.put(id, updated);
            }

            return exists;
        });
    }

    @Override
    public long updateByQuery(Query<ENTITY> query, Update<ENTITY> update) {
        return lockAndPerform(map -> {
            AtomicLong counter = new AtomicLong(0L);
            listMatching(query)
                    .forEach(entity -> {
                        updateById(entity.getId(), update);
                        counter.incrementAndGet();
                    });

            return counter.get();
        });
    }

    @Override
    public UpsertResult upsert(ENTITY entity) {
        return lockAndPerform(map -> map.put(entity.getId(), entity) == null
                ? UpsertResult.INSERT
                : UpsertResult.UPDATE
        );
    }

    @Override
    public Optional<ENTITY> deleteById(ID id) {
        return lockAndPerform(map -> Optional.ofNullable(map.remove(id)));
    }

    @Override
    public long deleteByQuery(Query<ENTITY> query) {
        return lockAndPerform(map -> {
            AtomicLong counter = new AtomicLong(0L);
            listMatching(query)
                    .forEach(entity -> {
                        deleteById(entity.getId());
                        counter.incrementAndGet();
                    });

            return counter.get();
        });
    }

    private <T> T lockAndPerform(Function<ConcurrentMap<ID, ENTITY>, T> consumer) {
        synchronized (lock) {
            return consumer.apply(map);
        }
    }
}
