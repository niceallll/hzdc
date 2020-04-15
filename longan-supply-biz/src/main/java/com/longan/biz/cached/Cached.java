package com.longan.biz.cached;

import java.util.List;
import java.util.Map;

public interface Cached<K, V> {
    void init();

    void reload();

    V get(K k);

    Map<K, V> getMap();

    List<V> getList();

    void clear();

    // void set(K k, V v);

    // void remove(K v);
}
