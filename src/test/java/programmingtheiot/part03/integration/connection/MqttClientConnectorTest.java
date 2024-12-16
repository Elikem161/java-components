package programmingtheiot.part03.integration.connection;
 
import static org.junit.Assert.*;
 
import java.util.logging.Logger;
 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 
import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.ActuatorData;
import programmingtheiot.data.DataUtil;
import programmingtheiot.gda.connection.MqttClientConnector;
 
/**
* This test case class contains integration tests for MqttClientConnector.
*/
public class MqttClientConnectorTest {
    // Static Logger
    private static final Logger _Logger = Logger.getLogger(MqttClientConnectorTest.class.getName());
 
    // MQTT Client Connector
    private MqttClientConnector mqttClient;
 
    // Test Setup
    @Before
    public void setUp() throws Exception {
        this.mqttClient = new MqttClientConnector();
    }
 
    @After
    public void tearDown() throws Exception {
        this.mqttClient.disconnectClient();
    }
 
    /**
     * Test connecting and disconnecting from the broker.
     */
    //@Test
    public void testConnectAndDisconnect() {
        assertTrue(this.mqttClient.connectClient());
        assertTrue(this.mqttClient.disconnectClient());
    }
 
    /**
     * Test publishing and subscribing to various topics.
     */
    //@Test
    public void testPublishAndSubscribe() {
        int qos = 0;
 
        assertTrue(this.mqttClient.connectClient());
        assertTrue(this.mqttClient.subscribeToTopic(ResourceNameEnum.GDA_MGMT_STATUS_MSG_RESOURCE, qos));
        
        String testMessage = "Test payload for GDA_MGMT_STATUS_MSG_RESOURCE.";
        assertTrue(this.mqttClient.publishMessage(ResourceNameEnum.GDA_MGMT_STATUS_MSG_RESOURCE, testMessage, qos));
 
        try {
            Thread.sleep(3000); // Wait for potential message processing
        } catch (InterruptedException e) {
            // Ignore
        }
 
        assertTrue(this.mqttClient.unsubscribeFromTopic(ResourceNameEnum.GDA_MGMT_STATUS_MSG_RESOURCE));
        assertTrue(this.mqttClient.disconnectClient());
    }
 
    /**
     * Test actuator response message subscription and processing.
     */
    @Test
    public void testActuatorCommandResponseSubscription() {
        int qos = 0;
 
        assertTrue(this.mqttClient.connectClient());
        try {
            Thread.sleep(2000); // Allow connection stabilization
        } catch (InterruptedException e) {
            // Ignore
        }
 
        ActuatorData ad = new ActuatorData();
        ad.setValue(12.3f);
        ad.setAsResponse();
 
        String adJson = DataUtil.getInstance().actuatorDataToJson(ad);
        assertTrue(this.mqttClient.publishMessage(ResourceNameEnum.CDA_ACTUATOR_RESPONSE_RESOURCE, adJson, qos));
 
        try {
            Thread.sleep(2000); // Allow message processing
        } catch (InterruptedException e) {
            // Ignore
        }
 
        assertTrue(this.mqttClient.disconnectClient());
    }
}
 
 