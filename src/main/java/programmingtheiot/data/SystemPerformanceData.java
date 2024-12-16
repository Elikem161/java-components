package programmingtheiot.data;

import java.io.Serializable;
import programmingtheiot.common.ConfigConst;

/**
 * Class representing system performance data.
 */
public class SystemPerformanceData extends BaseIotData implements Serializable {
    private static final long serialVersionUID = 1L; // Unique serialVersionUID

    // Private variables
    private float cpuUtil = ConfigConst.DEFAULT_VAL;
    private float diskUtil = ConfigConst.DEFAULT_VAL;
    private float memUtil = ConfigConst.DEFAULT_VAL;

    // Constructors
    public SystemPerformanceData() {
        super();
        super.setName(ConfigConst.SYS_PERF_DATA);
    }

    // Public methods
    public float getCpuUtilization() {
        return cpuUtil;
    }

    public float getDiskUtilization() {
        return diskUtil;
    }

    public float getMemoryUtilization() {
        return memUtil;
    }

    public void setCpuUtilization(float val) {
        if (val < 0.0f || val > 100.0f) {
            throw new IllegalArgumentException("CPU utilization must be between 0.0 and 100.0");
        }
        updateTimeStamp();
        this.cpuUtil = val;
    }

    public void setDiskUtilization(float val) {
        if (val < 0.0f || val > 100.0f) {
            throw new IllegalArgumentException("Disk utilization must be between 0.0 and 100.0");
        }
        updateTimeStamp();
        this.diskUtil = val;
    }

    public void setMemoryUtilization(float val) {
        if (val < 0.0f || val > 100.0f) {
            throw new IllegalArgumentException("Memory utilization must be between 0.0 and 100.0");
        }
        updateTimeStamp();
        this.memUtil = val;
    }

    /**
     * Returns a string representation of this instance in CSV format.
     * @return String The string representing this instance.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        
        sb.append(',');
        sb.append(ConfigConst.CPU_UTIL_PROP).append('=').append(this.getCpuUtilization()).append(',');
        sb.append(ConfigConst.DISK_UTIL_PROP).append('=').append(this.getDiskUtilization()).append(',');
        sb.append(ConfigConst.MEM_UTIL_PROP).append('=').append(this.getMemoryUtilization());
        
        return sb.toString();
    }

    // Protected methods
    @Override
    protected void handleUpdateData(BaseIotData data) {
        if (data instanceof SystemPerformanceData) {
            SystemPerformanceData spData = (SystemPerformanceData) data;
            this.setCpuUtilization(spData.getCpuUtilization());
            this.setDiskUtilization(spData.getDiskUtilization());
            this.setMemoryUtilization(spData.getMemoryUtilization());
        }
    }
}
