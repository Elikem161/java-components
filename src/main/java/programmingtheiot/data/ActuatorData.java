package programmingtheiot.data;

import java.io.Serializable;
import programmingtheiot.common.ConfigConst;

/**
 * Class representing actuator data.
 */
public class ActuatorData extends BaseIotData implements Serializable {
    private static final long serialVersionUID = 1L; // Unique serialVersionUID

    // Class-scoped variables
    private int command = ConfigConst.DEFAULT_COMMAND;
    private float value = ConfigConst.DEFAULT_VAL;
    private boolean isResponse = false;
    private String stateData = "";

    // Constructors
    public ActuatorData() {
        super();
    }

    // Public methods
    public int getCommand() {
        return this.command;
    }

    public float getValue() {
        return this.value;
    }

    public boolean isResponseFlagEnabled() {
        return this.isResponse;
    }

    public String getStateData() {
        return this.stateData;
    }

    public void setAsResponse() {
        updateTimeStamp();
        this.isResponse = true;
    }

    public void setCommand(int command) {
        updateTimeStamp();
        this.command = command; // Add validation if necessary
    }

    public void setValue(float val) {
        updateTimeStamp();
        this.value = val; // Add validation if necessary
    }

    public void setStateData(String stateData) {
        updateTimeStamp();
        if (stateData != null) {
            this.stateData = stateData;
        }
    }

    /**
     * Returns a string representation of this instance in CSV format.
     * @return String The string representing this instance.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        
        sb.append(',');
        sb.append(ConfigConst.COMMAND_PROP).append('=').append(this.getCommand()).append(',');
        sb.append(ConfigConst.IS_RESPONSE_PROP).append('=').append(this.isResponseFlagEnabled()).append(',');
        sb.append(ConfigConst.VALUE_PROP).append('=').append(this.getValue()).append(',');
        sb.append(ConfigConst.STATE_DATA_PROP).append('=').append(this.getStateData());
        
        return sb.toString();
    }

    // Protected methods
    @Override
    protected void handleUpdateData(BaseIotData data) {
        if (data instanceof ActuatorData) {
            ActuatorData aData = (ActuatorData) data;
            this.setCommand(aData.getCommand());
            this.setValue(aData.getValue());
            this.setStateData(aData.getStateData());
            if (aData.isResponseFlagEnabled()) {
                this.isResponse = true;
            }
        }
    }
}
