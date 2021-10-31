package net.pvytykac.db.impl;

import java.util.function.Predicate;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class Query<ENTITY> implements Predicate<ENTITY> {

    private final Predicate<ENTITY> predicate;

    private Query(Predicate<ENTITY> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(ENTITY entity) {
        return predicate.test(entity);
    }

    @Override
    public Predicate<ENTITY> and(Predicate<? super ENTITY> other) {
        return predicate.and(other);
    }

    @Override
    public Predicate<ENTITY> negate() {
        return predicate.negate();
    }

    @Override
    public Predicate<ENTITY> or(Predicate<? super ENTITY> other) {
        return predicate.or(other);
    }
}
