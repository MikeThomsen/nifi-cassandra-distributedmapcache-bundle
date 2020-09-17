package org.apache.nifi

import org.apache.nifi.processor.AbstractProcessor
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.exception.ProcessException
import org.apache.nifi.service.CassandraSessionProvider
import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Before
import org.junit.Test

/**
 * Setup instructions:
 *
 * docker run -p 7000:7000 -p 9042:9042 --name cassandra --restart always -d cassandra:3
 */
class CassandraDistributedMapCacheIT {
    TestRunner runner

    @Before
    void setup() {
        runner = TestRunners.newTestRunner(new AbstractProcessor() {
            @Override
            void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {

            }
        })
        def cassandraService = new CassandraSessionProvider()
        runner.addControllerService("provider", cassandraService)
        runner.setProperty(cassandraService, CassandraSessionProvider.CONTACT_POINTS, "localhost:9042")
        runner.setProperty(cassandraService, CassandraSessionProvider.KEYSPACE, "nifi_test")
        runner.enableControllerService(cassandraService)
        runner.assertValid()
    }

    @Test
    void testContainsKey() {

    }
}
