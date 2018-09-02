package com.jujutsu.me;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU Cache
 *
 * @param <K> <K>
 * @param <V> <V>
 * @author hjl
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Map<K, V> {

    /**
     * 最大缓存数
     */
    private int cacheMaxSize;

    public LRULinkedHashMap(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    @Override
    public boolean removeEldestEntry(Entry<K, V> eldest) {
        return size() > cacheMaxSize;
    }

}
