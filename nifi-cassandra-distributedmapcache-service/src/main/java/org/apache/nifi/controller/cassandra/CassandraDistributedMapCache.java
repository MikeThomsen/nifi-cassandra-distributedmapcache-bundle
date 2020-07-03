package org.apache.nifi.controller.cassandra;

import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.distributed.cache.client.Deserializer;
import org.apache.nifi.distributed.cache.client.DistributedMapCacheClient;
import org.apache.nifi.distributed.cache.client.Serializer;

import java.io.IOException;

@Tags({"map", "cache", "distributed", "cassandra"})
public class CassandraDistributedMapCache extends AbstractControllerService implements DistributedMapCacheClient {
    @Override
    public <K, V> boolean putIfAbsent(K k, V v, Serializer<K> serializer, Serializer<V> serializer1) throws IOException {
        return false;
    }

    @Override
    public <K, V> V getAndPutIfAbsent(K k, V v, Serializer<K> serializer, Serializer<V> serializer1, Deserializer<V> deserializer) throws IOException {
        return null;
    }

    @Override
    public <K> boolean containsKey(K k, Serializer<K> serializer) throws IOException {
        return false;
    }

    @Override
    public <K, V> void put(K k, V v, Serializer<K> serializer, Serializer<V> serializer1) throws IOException {

    }

    @Override
    public <K, V> V get(K k, Serializer<K> serializer, Deserializer<V> deserializer) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public <K> boolean remove(K k, Serializer<K> serializer) throws IOException {
        return false;
    }

    @Override
    public long removeByPattern(String s) throws IOException {
        return 0;
    }
}
