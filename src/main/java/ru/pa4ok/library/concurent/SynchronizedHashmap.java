package rip.ethereal.generator.util.synchronize;

import java.util.HashMap;

public class SynchronizedHashmap<K, V> extends HashMap<K, V>
{
    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }
}
