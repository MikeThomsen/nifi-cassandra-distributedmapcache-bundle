package org.apache.nifi.controller.cassandra;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnDisabled;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.cassandra.CassandraSessionProviderService;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.distributed.cache.client.Deserializer;
import org.apache.nifi.distributed.cache.client.DistributedMapCacheClient;
import org.apache.nifi.distributed.cache.client.Serializer;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Tags({"map", "cache", "distributed", "cassandra"})
@CapabilityDescription("Provides a DistributedMapCache client that is based on Apache Cassandra.")
public class CassandraDistributedMapCache extends AbstractControllerService implements DistributedMapCacheClient {
    public static final PropertyDescriptor SESSION_PROVIDER = new PropertyDescriptor.Builder()
            .name("cassandra-dmc-session-provider")
            .displayName("Session Provider")
            .description("The client service that will configure the cassandra client connection.")
            .required(true)
            .identifiesControllerService(CassandraSessionProviderService.class)
            .build();

    public static final PropertyDescriptor TABLE_NAME = new PropertyDescriptor.Builder()
            .name("cassandra-dmc-table-name")
            .displayName("Table Name")
            .description("The name of the table where the cache will be stored.")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_EL_VALIDATOR)
            .build();

    public static final PropertyDescriptor KEY_FIELD_NAME = new PropertyDescriptor.Builder()
            .name("cassandra-dmc-key-field-name")
            .displayName("Key Field Name")
            .description("The name of the field that acts as the unique key. (The CQL type should be \"text\")")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_EL_VALIDATOR)
            .build();

    public static final PropertyDescriptor VALUE_FIELD_NAME = new PropertyDescriptor.Builder()
            .name("cassandra-dmc-value-field-name")
            .displayName("Value Field Name")
            .description("The name of the field that will store the value. (The CQL type should be \"blob\")")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_EL_VALIDATOR)
            .build();

    public static final PropertyDescriptor TTL = new PropertyDescriptor.Builder()
            .name("cassandra-dmc-ttl")
            .displayName("TTL")
            .description("If configured, this will set a TTL (Time to Live) for each row inserted into the table so that " +
                    "old cache items expire after a certain period of time.")
            .addValidator(StandardValidators.TIME_PERIOD_VALIDATOR)
            .required(false)
            .build();

    public static final List<PropertyDescriptor> DESCRIPTORS = Collections.unmodifiableList(Arrays.asList(
        SESSION_PROVIDER, TABLE_NAME, KEY_FIELD_NAME, VALUE_FIELD_NAME, TTL
    ));

    private CassandraSessionProviderService sessionProviderService;
    private String tableName;
    private String keyField;
    private String valueField;
    private Long ttl;

    private Session session;

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return DESCRIPTORS;
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        sessionProviderService = context.getProperty(SESSION_PROVIDER).asControllerService(CassandraSessionProviderService.class);
        tableName = context.getProperty(TABLE_NAME).evaluateAttributeExpressions().getValue();
        keyField = context.getProperty(KEY_FIELD_NAME).evaluateAttributeExpressions().getValue();
        valueField = context.getProperty(VALUE_FIELD_NAME).evaluateAttributeExpressions().getValue();
        if (context.getProperty(TTL).isSet()) {
            ttl = context.getProperty(TTL).evaluateAttributeExpressions().asTimePeriod(TimeUnit.SECONDS);
        }

        session = sessionProviderService.getCassandraSession();
    }

    @OnDisabled
    public void onDisabled() {
        session.close();
    }

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
        String query = String.format("");

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
