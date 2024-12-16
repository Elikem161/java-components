/**
 * This class is part of the Programming the Internet of Things
 * project, and is available via the MIT License, which can be
 * found in the LICENSE file at the top level of this repository.
 * 
 * Copyright (c) 2020 by Andrew D. King
 */ 

package programmingtheiot.part03.integration.connection;

import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.DefaultDataMessageListener;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.DataUtil;
import programmingtheiot.data.SensorData;
import programmingtheiot.gda.connection.CoapClientConnector;

/**
 * This test case class contains basic performance tests for
 * CoapClientConnector. It should serve as a starting point for
 * additional functionality tests.
 * 
 * NOTE: The CoAP server must be running before executing these tests.
 */
public class CoapClientPerformanceTest {
	
	// Static constants
	public static final int DEFAULT_TIMEOUT = 5;
	public static final int MAX_TEST_RUNS = 100;  // Limit to 10,000 requests for performance tests
	
	private static final Logger _Logger = Logger.getLogger(CoapClientPerformanceTest.class.getName());

	// Member variables
	private CoapClientConnector coapClient = null;
	private IDataMessageListener dataMsgListener = null;
	
	// Test setup methods
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Initialization code if needed (global setup)
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Clean-up code if needed (global teardown)
	}
	
	@Before
	public void setUp() throws Exception {
		// Initialize the CoapClientConnector and the data listener
		this.coapClient = new CoapClientConnector();
		this.dataMsgListener = new DefaultDataMessageListener();
		
		// Set the listener for data messages
		this.coapClient.setDataMessageListener(this.dataMsgListener);
	}
	
	@After
	public void tearDown() throws Exception {
		// Clean up resources after each test (if needed)
		// This can be left empty or used for client disconnection if necessary
	}
	
	// Test methods for POST requests with CON (Confirmable) and NON (Non-Confirmable) messages
	
	@Test
	public void testPostRequestCon() {
		_Logger.info("Testing POST - CON");
		execTestPost(MAX_TEST_RUNS, true);
	}
	
	@Test
	public void testPostRequestNon() {
		_Logger.info("Testing POST - NON");
		execTestPost(MAX_TEST_RUNS, false);
	}
	
	// Test methods for PUT requests with CON (Confirmable) and NON (Non-Confirmable) messages
	
	//@Test
	public void testPutRequestCon() {
		_Logger.info("Testing PUT - CON");
		execTestPut(MAX_TEST_RUNS, true);
	}
	
	//@Test
	public void testPutRequestNon() {
		_Logger.info("Testing PUT - NON");
		execTestPut(MAX_TEST_RUNS, false);
	}
	
	// Private helper methods for executing POST and PUT requests
	
	private void execTestPost(int maxTestRuns, boolean enableCON) {
		SensorData sd = new SensorData();
		String payload = DataUtil.getInstance().sensorDataToJson(sd);
		
		long startMillis = System.currentTimeMillis();
		
		// Loop to send the POST requests
		for (int seqNo = 0; seqNo < maxTestRuns; seqNo++) {
			this.coapClient.sendPostRequest(ResourceNameEnum.CDA_SENSOR_MSG_RESOURCE, ConfigConst.TEMP_SENSOR_NAME, enableCON, payload, DEFAULT_TIMEOUT);
		}
		
		long endMillis = System.currentTimeMillis();
		long elapsedMillis = endMillis - startMillis;
		
		_Logger.info("POST message - useCON = " + enableCON + " [" + maxTestRuns + "]: " + elapsedMillis + " ms");
	}
	//@Test
	private void execTestPut(int maxTestRuns, boolean enableCON) {
		SensorData sd = new SensorData();
		String payload = DataUtil.getInstance().sensorDataToJson(sd);
		
		long startMillis = System.currentTimeMillis();
		
		// Loop to send the PUT requests
		for (int seqNo = 0; seqNo < maxTestRuns; seqNo++) {
			this.coapClient.sendPutRequest(ResourceNameEnum.CDA_SENSOR_MSG_RESOURCE, ConfigConst.TEMP_SENSOR_NAME, enableCON, payload, DEFAULT_TIMEOUT);
		}
		
		long endMillis = System.currentTimeMillis();
		long elapsedMillis = endMillis - startMillis;
		
		_Logger.info("PUT message - useCON = " + enableCON + " [" + maxTestRuns + "]: " + elapsedMillis + " ms");
	}
}
