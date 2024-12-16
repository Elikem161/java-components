package programmingtheiot.data;
 
import java.nio.file.FileSystems;

import java.nio.file.Files;

import java.nio.file.Path;

import java.util.ArrayList;

import java.util.List;

import java.util.logging.Level;

import java.util.logging.Logger;
 
import com.google.gson.Gson;
 
import programmingtheiot.common.ConfigConst;
 
/**

* Utility class for converting data classes to and from JSON format.

*/

public class DataUtil {

    // Static fields

    private static final Logger _Logger = Logger.getLogger(DataUtil.class.getName());

    private static final DataUtil _Instance = new DataUtil();

    private Gson gson; // Gson instance for JSON conversion
 
    // Private constructor for Singleton pattern

    private DataUtil() {

        gson = new Gson();

    }
 
    // Method to get the Singleton instance

    public static final DataUtil getInstance() {

        return _Instance;

    }
 
    // Convert ActuatorData to JSON

    public String actuatorDataToJson(ActuatorData data) {

        String jsonData = null;
 
        if (data != null) {

            jsonData = gson.toJson(data);

            _Logger.info("ActuatorData converted to JSON: " + jsonData);

        } else {

            _Logger.warning("ActuatorData input is null, cannot convert to JSON.");

        }
 
        return jsonData;

    }
 
    // Convert JSON to ActuatorData

    public ActuatorData jsonToActuatorData(String jsonData) {

        ActuatorData data = null;
 
        if (jsonData != null && !jsonData.trim().isEmpty()) {

            data = gson.fromJson(jsonData, ActuatorData.class);

            _Logger.info("JSON converted to ActuatorData: " + data);

        } else {

            _Logger.severe("Input JSON data is null or empty.");

        }
 
        return data;

    }
 
    // Convert SensorData to JSON

    public String sensorDataToJson(SensorData data) {

        String jsonData = null;
 
        if (data != null) {

            jsonData = gson.toJson(data);

            _Logger.info("SensorData converted to JSON: " + jsonData);

        } else {

            _Logger.warning("SensorData input is null, cannot convert to JSON.");

        }
 
        return jsonData;

    }
 
    // Convert JSON to SensorData

    public SensorData jsonToSensorData(String jsonData) {

        SensorData data = null;
 
        if (jsonData != null && !jsonData.trim().isEmpty()) {

            data = gson.fromJson(jsonData, SensorData.class);

            _Logger.info("JSON converted to SensorData: " + data);

        } else {

            _Logger.severe("Input JSON data is null or empty.");

        }
 
        return data;

    }
 
    // Convert SystemPerformanceData to JSON

    public String systemPerformanceDataToJson(SystemPerformanceData data) {

        String jsonData = null;
 
        if (data != null) {

            jsonData = gson.toJson(data);

            _Logger.info("SystemPerformanceData converted to JSON: " + jsonData);

        } else {

            _Logger.warning("SystemPerformanceData input is null, cannot convert to JSON.");

        }
 
        return jsonData;

    }
 
    // Convert JSON to SystemPerformanceData

    public SystemPerformanceData jsonToSystemPerformanceData(String jsonData) {

        SystemPerformanceData data = null;
 
        if (jsonData != null && !jsonData.trim().isEmpty()) {

            data = gson.fromJson(jsonData, SystemPerformanceData.class);

            _Logger.info("JSON converted to SystemPerformanceData: " + data);

        } else {

            _Logger.severe("Input JSON data is null or empty.");

        }
 
        return data;

    }
 
    // Optionally, include methods for SystemStateData if needed

    // Convert SystemStateData to JSON

    public String systemStateDataToJson(SystemStateData data) {

        String jsonData = null;
 
        if (data != null) {

            jsonData = gson.toJson(data);

            _Logger.info("SystemStateData converted to JSON: " + jsonData);

        } else {

            _Logger.warning("SystemStateData input is null, cannot convert to JSON.");

        }
 
        return jsonData;

    }
 
    // Convert JSON to SystemStateData

    public SystemStateData jsonToSystemStateData(String jsonData) {

        SystemStateData data = null;
 
        if (jsonData != null && !jsonData.trim().isEmpty()) {

            data = gson.fromJson(jsonData, SystemStateData.class);

            _Logger.info("JSON converted to SystemStateData: " + data);

        } else {

            _Logger.severe("Input JSON data is null or empty.");

        }
 
        return data;

    }

}

 