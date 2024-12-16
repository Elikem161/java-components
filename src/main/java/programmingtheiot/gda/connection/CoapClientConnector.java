/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.connection;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;

import programmingtheiot.data.DataUtil;
import programmingtheiot.gda.connection.handlers.GenericCoapResponseHandler;
import programmingtheiot.gda.connection.handlers.SensorDataObserverHandler;
import programmingtheiot.gda.connection.handlers.SystemPerformanceDataObserverHandler;

/**
 * Shell representation of class for student implementation.
 *
 */
public class CoapClientConnector implements IRequestResponseClient
{
	// static
	
	private static final Logger _Logger =
		Logger.getLogger(CoapClientConnector.class.getName());
	private String     protocol;
	private String     host;
	private int        port;
	private String     serverAddr;
	private CoapClient clientConn;
	private IDataMessageListener dataMsgListener;
	// params
	
	
	// constructors
	
	/**
	 * Default.
	 * 
	 * All config data will be loaded from the config file.
	 */
	public CoapClientConnector()
	{

		ConfigUtil config = ConfigUtil.getInstance();
		this.host = config.getProperty(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.HOST_KEY, ConfigConst.DEFAULT_HOST);

		if (config.getBoolean(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.ENABLE_CRYPT_KEY)) {
			this.protocol = ConfigConst.DEFAULT_COAP_SECURE_PROTOCOL;
			this.port     = config.getInteger(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.SECURE_PORT_KEY, ConfigConst.DEFAULT_COAP_SECURE_PORT);
		} else {
			this.protocol = ConfigConst.DEFAULT_COAP_PROTOCOL;
			this.port     = config.getInteger(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.PORT_KEY, ConfigConst.DEFAULT_COAP_PORT);
		}
		
		// NOTE: URL does not have a protocol handler for "coap",
		// so we need to construct the URL manually
		this.serverAddr = this.protocol + "://" + this.host + ":" + this.port;

		initClient();

		_Logger.info("Using URL for server conn: " + this.serverAddr);
	}
		
	/**
	 * Constructor.
	 * 
	 * @param host
	 * @param isSecure
	 * @param enableConfirmedMsgs
	 */
	public CoapClientConnector(String host, boolean isSecure, boolean enableConfirmedMsgs)
	{
		
	}
	
	
	private void initClient() {
		// TODO Auto-generated method stub
		try {
			this.clientConn = new CoapClient(this.serverAddr);
			
			_Logger.info("Created client connection to server / resource: " + this.serverAddr);
		} catch (Exception e) {
			_Logger.info("Failed to connect to broker: " + (this.clientConn != null ? this.clientConn.getURI() : this.serverAddr));
		}
	}

	// public methods
	
	

	@Override
	public boolean sendDeleteRequest(ResourceNameEnum resource, String name, boolean enableCON, int timeout)
	{
		CoapResponse response = null;

		if (enableCON) {
			this.clientConn.useCONs();
		} else {
			this.clientConn.useNONs();
		}

		this.clientConn.setURI(this.serverAddr + "/" + resource.getResourceName());
		try {
			response = this.clientConn.delete();
		} catch (ConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response != null) {
			// TODO: implement your logic here
			
			_Logger.info("Handling DELETE. Response: " + response.isSuccess() + " - " + response.getOptions() + " - " +
				response.getCode() + " - " + response.getResponseText());
			
			if (this.dataMsgListener != null) {
				// TODO: implement this
			}
			
			return true;
		} else {
			_Logger.warning("Handling DELETE. No response received.");
		}

		return false;
	}

	@Override
	public boolean sendGetRequest(ResourceNameEnum resource, String name, boolean enableCON, int timeout)
	{
		CoapResponse response = null;

		if (enableCON) {
			this.clientConn.useCONs();
		} else {
			this.clientConn.useNONs();
		}

		this.clientConn.setURI(this.serverAddr + "/" + resource.getResourceName());
		try {
			response = this.clientConn.get();
		} catch (ConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response != null) {
			// TODO: implement your logic here
			
			_Logger.info("Handling GET. Response: " + response.isSuccess() + " - " + response.getOptions() + " - " +
				response.getCode() + " - " + response.getResponseText());
			
			if (this.dataMsgListener != null) {
				// TODO: implement this
			}
			
			return true;
		} else {
			_Logger.warning("Handling GET. No response received.");
		}

		return false;
	}

	@Override
	public boolean sendPostRequest(ResourceNameEnum resource, String name, boolean enableCON, String payload, int timeout)
	{
		CoapResponse response = null;

		if (enableCON) {
			this.clientConn.useCONs();
		} else {
			this.clientConn.useNONs();
		}

		this.clientConn.setURI(this.serverAddr + "/" + resource.getResourceName());

		// TODO: determine which MediaTypeRegistry const should be used for this call
		try {
			response = this.clientConn.post(payload, MediaTypeRegistry.TEXT_PLAIN);
		} catch (ConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response != null) {
			// TODO: implement your logic here
			
			_Logger.info("Handling POST. Response: " + response.isSuccess() + " - " + response.getOptions() + " - " +
				response.getCode() + " - " + response.getResponseText());
			
			if (this.dataMsgListener != null) {
				// TODO: implement this
			}
			
			return true;
		} else {
			_Logger.warning("Handling POST. No response received.");
		}

		return false;
	}

	@Override
	public boolean sendPutRequest(ResourceNameEnum resource, String name, boolean enableCON, String payload, int timeout)
	{
		CoapResponse response = null;

		if (enableCON) {
			this.clientConn.useCONs();
		} else {
			this.clientConn.useNONs();
		}

		this.clientConn.setURI(this.serverAddr + "/" + resource.getResourceName());

		// TODO: determine which MediaTypeRegistry const should be used for this call
		try {
			response = this.clientConn.put(payload, MediaTypeRegistry.TEXT_PLAIN);
		} catch (ConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response != null) {
			// TODO: implement your logic here
			
			_Logger.info("Handling PUT. Response: " + response.isSuccess() + " - " + response.getOptions() + " - " +
				response.getCode() + " - " + response.getResponseText());
			
			if (this.dataMsgListener != null) {
				// TODO: implement this
			}
			
			return true;
		} else {
			_Logger.warning("Handling PUT. No response received.");
		}

		return false;
	}

	@Override
	public boolean setDataMessageListener(IDataMessageListener listener) {
	    this.dataMsgListener = listener;
		return false;
	}

	public void clearEndpointPath()
	{
	}
	
	public void setEndpointPath(ResourceNameEnum resource)
	{
	}
	
	@Override
	public boolean startObserver(ResourceNameEnum resource, String name, int ttl) {
		String uriPath = this.serverAddr + "/" + resource.getResourceName();
		if(name!=null) uriPath = uriPath + name;
	    _Logger.info("Observing resource [START]: " + uriPath);

	    this.clientConn.setURI(uriPath);

	    CoapHandler handler;
	    if (resource == ResourceNameEnum.CDA_SENSOR_MSG_RESOURCE) {
	        SensorDataObserverHandler sensorHandler = new SensorDataObserverHandler();
	        sensorHandler.setDataMessageListener(this.dataMsgListener);
	        handler = sensorHandler;
	    } else if (resource == ResourceNameEnum.CDA_SYSTEM_PERF_MSG_RESOURCE) {
	        SystemPerformanceDataObserverHandler sysHandler = new SystemPerformanceDataObserverHandler();
	        sysHandler.setDataMessageListener(this.dataMsgListener);
	        handler = sysHandler;
	    } else {
	        _Logger.warning("Unsupported resource type for OBSERVE.");
	        return false;
	    }

	    CoapObserveRelation relation = this.clientConn.observe(handler);
	    return (!relation.isCanceled());
	}


	@Override
	public boolean stopObserver(ResourceNameEnum resourceType, String name, int timeout)
	{
		return false;
	}

	@Override
	
	public boolean sendDiscoveryRequest(int timeout)
    {
        _Logger.info("sendDiscoveryRequest() called with timeout = " + timeout);
        Set<WebLink> wlSet = null;
        if(this.clientConn==null) _Logger.info("why");
		try {
			wlSet = this.clientConn.discover();
		} catch (ConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        if (wlSet != null) {
            for (WebLink wl : wlSet) {
                _Logger.info(" --> URI: " + wl.getURI() + ". Attributes: " + wl.getAttributes());
            }
            return true;
        }
        return false;
    }



	

	@Override
	public boolean sendDiscoveryRequest() {
		// TODO Auto-generated method stub
		
		return false;
	}

	

	

	
	// private methods
	
}