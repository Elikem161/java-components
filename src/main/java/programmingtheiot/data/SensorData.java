package programmingtheiot.data;

import java.io.Serializable;
import programmingtheiot.common.ConfigConst;

/**
 * Class representing sensor data.
 */
public class SensorData extends BaseIotData implements Serializable {
    private static final long serialVersionUID = 1L; // Unique serialVersionUID

    // Class-scoped variables
    private float value = ConfigConst.DEFAULT_VAL;

    // Constructors
    public SensorData() {
        super();
    }

    public SensorData(int sensorType) {
        super();
        // You can handle sensorType initialization if necessary
    }

    // Public methods
    public float getValue() {
        return this.value;
    }

    public void setValue(float val) {
        updateTimeStamp();
        // Add validation if necessary, e.g., checking range
        this.value = val;
    }

    /**
     * Returns a string representation of this instance in CSV format.
     * @return String The string representing this instance.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());

        sb.append(',');
        sb.append(ConfigConst.VALUE_PROP).append('=').append(this.getValue());

        return sb.toString();
    }

    // Protected methods
    @Override
    protected void handleUpdateData(BaseIotData data) {
        if (data instanceof SensorData) {
            SensorData sData = (SensorData) data;
            this.setValue(sData.getValue());
        }
    }
}
