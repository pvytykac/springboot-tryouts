package net.pvytykac.db.impl;

import java.util.function.Function;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class Update<ENTITY> implements Function<ENTITY, ENTITY> {

    private final Function<ENTITY, ENTITY> updateFunction;

    private Update(Function<ENTITY, ENTITY> updateFunction) {
        this.updateFunction = updateFunction;
    }
    
    public static <T> Update<T> of(Function<T, T> updateFunction) {
        return new Update<>(updateFunction);
    }

    @Override
    public ENTITY apply(ENTITY t) {
        return updateFunction.apply(t);
    }

    @Override
    public <V> Function<V, ENTITY> compose(Function<? super V, ? extends ENTITY> before) {
        return updateFunction.compose(before);
    }

    @Override
    public <V> Function<ENTITY, V> andThen(Function<? super ENTITY, ? extends V> after) {
        return updateFunction.andThen(after);
    }
}
