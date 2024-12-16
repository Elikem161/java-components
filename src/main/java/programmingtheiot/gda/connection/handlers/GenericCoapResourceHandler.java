package programmingtheiot.gda.connection.handlers;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;

public class GenericCoapResourceHandler extends CoapResource {
    private static final Logger _Logger = Logger.getLogger(GenericCoapResourceHandler.class.getName());
    private IDataMessageListener dataMsgListener = null;

    public GenericCoapResourceHandler(ResourceNameEnum resource) {
        this(resource.getResourceName());
    }

    public GenericCoapResourceHandler(String resourceName) {
        super(resourceName);
    }

    public void setDataMessageListener(IDataMessageListener listener) {
        if (listener != null) {
            this.dataMsgListener = listener;
        }
    }

    @Override
    public void handleDELETE(CoapExchange context) {
        _Logger.info("DELETE request received.");
        context.accept();
        context.respond(ResponseCode.DELETED, "Resource deleted");
    }

    @Override
    public void handleGET(CoapExchange context) {
        _Logger.info("GET request received.");
        context.accept();
        context.respond(ResponseCode.CONTENT, "Resource data");
    }

    @Override
    public void handlePOST(CoapExchange context) {
        _Logger.info("POST request received with payload: " + new String(context.getRequestPayload()));
        context.accept();
        context.respond(ResponseCode.CHANGED, "Resource updated");
    }

    @Override
    public void handlePUT(CoapExchange context) {
        _Logger.info("PUT request received with payload: " + new String(context.getRequestPayload()));
        context.accept();
        context.respond(ResponseCode.CHANGED, "Resource modified");
    }
}
