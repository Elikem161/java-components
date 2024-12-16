/**
 * 
 * This class is part of the Programming the Internet of Things
 * project, and is available via the MIT License, which can be
 * found in the LICENSE file at the top level of this repository.
 * 
 * Copyright (c) 2020 by Andrew D. King
 */ 

package programmingtheiot.part03.integration.connection;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.DataUtil;
import programmingtheiot.data.SensorData;
import programmingtheiot.gda.connection.MqttClientConnector;

/**
 * This test case class contains basic integration tests for
 * MqttClientPerformanceTest. It serves as a starting point for
 * additional functionality within the Programming the IoT environment.
 * 
 * IMPORTANT NOTE: This test expects MqttClientConnector to be
 * configured using the synchronous MqttClient.
 */
public class MqttClientPerformanceTest
{
    private static final Logger _Logger =
        Logger.getLogger(MqttClientPerformanceTest.class.getName());

    public static final int MAX_TEST_RUNS = 100;

    // Member variables
    private MqttClientConnector mqttClient = null;

    // Test setup methods

    /**
     * Set up the MQTT client for testing.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        ConfigUtil.getInstance(); // Load config
        this.mqttClient = new MqttClientConnector(); // Initialize the client
    }

    /**
     * Clean up after tests.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
        if (this.mqttClient != null) {
            this.mqttClient.disconnectClient(); // Ensure disconnection if not done already
        }
    }

    // Test methods

    /**
     * Test connect and disconnect functionality of the MQTT client.
     */
    @Test
    public void testConnectAndDisconnect()
    {
        long startMillis = System.currentTimeMillis();

        assertTrue(this.mqttClient.connectClient());
        assertTrue(this.mqttClient.disconnectClient());

        long endMillis = System.currentTimeMillis();
        long elapsedMillis = endMillis - startMillis;

        _Logger.info(String.format("Connect and Disconnect: %d ms", elapsedMillis));
    }

    /**
     * Test publishing with QoS 0.
     */
    @Test
    public void testPublishQoS0()
    {
        execTestPublish(MAX_TEST_RUNS, 0);
    }

    /**
     * Test publishing with QoS 1.
     */
    @Test
    public void testPublishQoS1()
    {
        execTestPublish(MAX_TEST_RUNS, 1);
    }

    /**
     * Test publishing with QoS 2.
     */
    @Test
    public void testPublishQoS2()
    {
        execTestPublish(MAX_TEST_RUNS, 2);
    }

    // Private helper methods

    /**
     * Execute a publish test with the specified number of runs and QoS.
     * @param maxTestRuns The number of test runs to perform.
     * @param qos The quality of service level (0, 1, or 2).
     */
    private void execTestPublish(int maxTestRuns, int qos)
    {
        assertTrue(this.mqttClient.connectClient());

        SensorData sensorData = new SensorData();
        String payload = DataUtil.getInstance().sensorDataToJson(sensorData);
        int payloadLen = payload.length();

        long startMillis = System.currentTimeMillis();

        // Publish the message for each test run
        for (int sequenceNo = 1; sequenceNo <= maxTestRuns; sequenceNo++) {
            this.mqttClient.publishMessage(ResourceNameEnum.CDA_MGMT_STATUS_CMD_RESOURCE, payload, qos);
        }

        long endMillis = System.currentTimeMillis();
        long elapsedMillis = endMillis - startMillis;

        // Disconnect after publishing all messages
        assertTrue(this.mqttClient.disconnectClient());

        // Log the result
        String msg = String.format(
            "\n\tTesting Publish: QoS = %d | msgs = %d | payload size = %d bytes | start = %.2f | end = %.2f | elapsed = %.2f sec",
            qos, maxTestRuns, payloadLen,
            startMillis / 1000.0, endMillis / 1000.0, elapsedMillis / 1000.0);

        _Logger.info(msg);
    }
}
