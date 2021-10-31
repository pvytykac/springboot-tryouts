package net.pvytykac.db.impl;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class DuplicateKeyException extends RuntimeException {

    public DuplicateKeyException(Object key) {
        super("Duplicate key '" + key + "'");
    }
}
