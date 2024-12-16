package programmingtheiot.gda.system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.SystemPerformanceData;
import java.util.logging.Logger;

/**
 * Manages system performance data collection and reporting.
 */
public class SystemPerformanceManager {
    // Private variables
    private ScheduledExecutorService schedExecSvc = null;
    private SystemCpuUtilTask sysCpuUtilTask = null;
    private SystemMemUtilTask sysMemUtilTask = null;
    private SystemDiskUtilTask sysDiskUtilTask = null; // New task for disk utilization

    private Runnable taskRunner = null;
    private boolean isStarted = false;
    private static final Logger _Logger = Logger.getLogger(SystemPerformanceManager.class.getName());
    private int pollRate = ConfigConst.DEFAULT_POLL_CYCLES;

    // New class-scoped variables
    private String locationID = ConfigConst.NOT_SET;
    private IDataMessageListener dataMsgListener = null;

    // Constructor
    public SystemPerformanceManager() {
        this.pollRate = ConfigUtil.getInstance().getInteger(
            ConfigConst.GATEWAY_DEVICE, ConfigConst.POLL_CYCLES_KEY, ConfigConst.DEFAULT_POLL_CYCLES);
        
        if (this.pollRate <= 0) {
            this.pollRate = ConfigConst.DEFAULT_POLL_CYCLES;
        }
        
        this.schedExecSvc = Executors.newScheduledThreadPool(1);
        this.sysCpuUtilTask = new SystemCpuUtilTask();
        this.sysMemUtilTask = new SystemMemUtilTask();
        this.sysDiskUtilTask = new SystemDiskUtilTask(); // Initialize disk utilization task
        
        // Retrieve location ID from config
        this.locationID = ConfigUtil.getInstance().getProperty(
            ConfigConst.GATEWAY_DEVICE, ConfigConst.LOCATION_ID_PROP, ConfigConst.NOT_SET);
        
        this.taskRunner = () -> {
            _Logger.info("Telemetry task running...");
            this.handleTelemetry();
        };
    }

    // Public methods
    public void handleTelemetry() {
        float cpuUtil = this.sysCpuUtilTask.getTelemetryValue();
        float memUtil = this.sysMemUtilTask.getTelemetryValue();
        float diskUtil = this.sysDiskUtilTask.getTelemetryValue(); // Get disk utilization
        
        // Log CPU, memory, and disk utilization
        _Logger.info("CPU utilization: " + cpuUtil + ", Mem utilization: " + memUtil + ", Disk utilization: " + diskUtil);
        
        // Create a new SystemPerformanceData instance and set values
        SystemPerformanceData spd = new SystemPerformanceData();
        spd.setLocationID(this.locationID);
        spd.setCpuUtilization(cpuUtil);
        spd.setMemoryUtilization(memUtil);
        spd.setDiskUtilization(diskUtil); // Set disk utilization
        
        // Invoke the data message listener if set
        if (this.dataMsgListener != null) {
            this.dataMsgListener.handleSystemPerformanceMessage(
                ResourceNameEnum.GDA_SYSTEM_PERF_MSG_RESOURCE, spd);
        }
    }
    
    public void setDataMessageListener(IDataMessageListener listener) {
        if (listener != null) {
            this.dataMsgListener = listener;
        }
    }
    
    public boolean startManager() {
        if (!this.isStarted) {
            _Logger.info("SystemPerformanceManager is starting...");
            
            ScheduledFuture<?> futureTask =
                this.schedExecSvc.scheduleAtFixedRate(this.taskRunner, 1L, this.pollRate, TimeUnit.SECONDS);
            
            this.isStarted = true;
        } else {
            _Logger.info("SystemPerformanceManager is already started.");
        }
        
        return this.isStarted;
    }
    
    public boolean stopManager() {
        this.schedExecSvc.shutdown();
        this.isStarted = false;
        
        _Logger.info("SystemPerformanceManager is stopped.");
        
        return true;
    }
}
