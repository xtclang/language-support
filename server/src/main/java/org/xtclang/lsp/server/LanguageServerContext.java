package org.xtclang.lsp.server;

public interface LanguageServerContext {

    <V> void put(LanguageServerContext.Key<V> key, V value);

    <V> V get(LanguageServerContext.Key<V> key);

    <V> void put(Class<V> clazz, V value);

    <V> V get(Class<V> clazz);

    /**
     * @param <K> key
     */
    class Key<K> {
    }
}
