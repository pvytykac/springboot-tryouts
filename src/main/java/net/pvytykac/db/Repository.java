package net.pvytykac.db;

import net.pvytykac.db.impl.DuplicateKeyException;
import net.pvytykac.db.impl.Query;
import net.pvytykac.db.impl.Update;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Paly
 * @since 2021-10-30
 */
public interface Repository<ID, ENTITY extends Entity<ID>> {

    // read

    long count();

    long countMatching(Query<ENTITY> query);

    Collection<ENTITY> listAll();

    Collection<ENTITY> listMatching(Query<ENTITY> query);

    Optional<ENTITY> findById(ID id);

    //write

    void insert(ENTITY entity) throws DuplicateKeyException;

    boolean updateById(ID id, Update<ENTITY> update);

    long updateByQuery(Query<ENTITY> query, Update<ENTITY> update);

    UpsertResult upsert(ENTITY entity);

    Optional<ENTITY> deleteById(ID id);

    long deleteByQuery(Query<ENTITY> query);

    enum UpsertResult {
        INSERT, UPDATE;
    }

}
