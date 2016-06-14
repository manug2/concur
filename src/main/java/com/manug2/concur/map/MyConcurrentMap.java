package com.manug2.concur.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by maverick on 4/11/2016.
 */
public class MyConcurrentMap<K, V> implements ConcurrentMap<K, V> {

    Object[] items = new Object[1*1000*1000];
    Object[] keys = new Object[1*1000*1000];
    int size = 0;

    //HashMap<K, V> _map = new HashMap<>(100);
    @Override
    public int size() {
        return size;
        //return _map.size();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
        //return _map.isEmpty();
    }

    @Override
    public boolean containsKey(Object k) {
        for (Object key : keys)
            if (key != null) {
                if (key.equals(k))
                    return true;
            }

        return false;
        //return _map.containsKey(key);
    }

    @Override
    public V get(Object k) {
        int index = -1;
        for (int i=0; i < keys.length; i++) {
            Object key = keys[i];
            if (key != null) {
                if (key.equals(k)) {
                    index = i;
                    break;
                }
            }
        }

        if (index>-1)
            return (V) items[index];
        else
            return null;
        //return _map.get(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Object item : items)
            if (item != null) {
                if (item.equals(value))
                    return true;
            }

        return false;
        //return _map.containsValue(value);
    }

    @Override
    public V put(K key, V value) {
        int key_hash = key.hashCode();
        keys[key_hash] = key;
        Object old = items[key_hash];
        if (old != null)
            ++size;
        else
            keys[key_hash] = key;
        items[key_hash] = value;
        return (V) old;
        //return _map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        int key_hash = key.hashCode();
        Object old = items[key_hash];
        if (old != null)
            ++size;
        else
            keys[key_hash] = key;

        items[key_hash] = value;
        return (V) old;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(K key, V value) {
        return null;
    }
}
