package az.pashabank.msparnik.service;

import az.pashabank.msparnik.context.DeviceContext;
import az.pashabank.msparnik.context.SensorContext;
import az.pashabank.msparnik.model.UnknownDeviceException;
import az.pashabank.msparnik.model.dto.DeviceStateChangeDto;
import az.pashabank.msparnik.model.dto.DeviceStatus;
import az.pashabank.msparnik.model.dto.PayloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static az.pashabank.msparnik.model.dto.DeviceStatus.*;

@Service
@RequiredArgsConstructor
public class MainService {
    private final DeviceContext deviceContext;
    private final SensorContext sensorContext;

    public List<DeviceStatus> handleAllPayloads(List<PayloadDto> payloads) {
        var statuses = new ArrayList<DeviceStatus>();
        payloads.forEach(payload -> statuses.addAll(handlePayload(payload)));
        return statuses;
    }

    private List<DeviceStatus> handlePayload(PayloadDto payload) {
        return switch (payload.getQuantity()) {
            case "temperature" -> handleTemperature(payload);
            case "humidity" -> handleHumidity(payload);
            case "moisture" -> handleMoisture(payload);
            default -> throw new UnknownDeviceException("Unknown device: " + payload.getQuantity());
        };
    }

    private List<DeviceStatus> handleTemperature(PayloadDto payload) {
        var tempValue = payload.getValue();
        sensorContext.setTEMPERATURE(tempValue);

        if (tempValue.compareTo(sensorContext.TEMP_THRESHOLD_LOW) == -1) {
            deviceContext.setLED(ACTIVE);
            deviceContext.setFAN(PASSIVE);
        }
        else if (tempValue.compareTo(sensorContext.TEMP_THRESHOLD_HIGH) == 1)
        {
            deviceContext.setLED(PASSIVE);
            deviceContext.setFAN(ACTIVE);
        }

        else {
            deviceContext.setLED(PASSIVE);
            deviceContext.setFAN(PASSIVE);
        }

        return List.of(deviceContext.getLED(), deviceContext.getFAN());
    }

    private List<DeviceStatus> handleMoisture(PayloadDto payload) {
        var moistureValue = payload.getValue();
        sensorContext.setMOISTURE(moistureValue);

        if (moistureValue.compareTo(sensorContext.MOISTURE_THRESHOLD) == 1) deviceContext.setPUMP(PASSIVE);
        else deviceContext.setPUMP(ACTIVE);

        return List.of(deviceContext.getPUMP());
    }

    private List<DeviceStatus> handleHumidity(PayloadDto payload) {
        var humidityValue = payload.getValue();
        sensorContext.setHUMIDITY(humidityValue);

        if (humidityValue.compareTo(sensorContext.HUMIDITY_THRESHOLD) == 1) deviceContext.setMOTOR(PASSIVE);
        else deviceContext.setMOTOR(ACTIVE);

        return List.of(deviceContext.getMOTOR());
    }

    public Map<String, BigDecimal> getSensorData() {
        var sensorMap = new HashMap<String, BigDecimal>();

        sensorMap.put("temperature", sensorContext.getTEMPERATURE());
        sensorMap.put("moisture", sensorContext.getMOISTURE());
        sensorMap.put("humidity", sensorContext.getHUMIDITY());

        return sensorMap;
    }

    public Map<String, DeviceStatus> getDeviceData() {
        var deviceMap = new HashMap<String, DeviceStatus>();

        deviceMap.put("led", deviceContext.getLED());
        deviceMap.put("fan", deviceContext.getFAN());
        deviceMap.put("pump", deviceContext.getPUMP());
        deviceMap.put("motor", deviceContext.getMOTOR());

        return deviceMap;
    }

    public void changeDeviceState(String deviceName, DeviceStateChangeDto stateChange) {
        switch (deviceName) {
            case "led" -> deviceContext.setLED(stateChange.getState());
            case "pump" -> deviceContext.setPUMP(stateChange.getState());
            case "fan" -> deviceContext.setFAN(stateChange.getState());
            case "motor" -> deviceContext.setMOTOR(stateChange.getState());
        }
    }

    public List<DeviceStatus> getDeviceStatuses() {
        return List.of(
                deviceContext.getLED(),
                deviceContext.getFAN(),
                deviceContext.getMOTOR(),
                deviceContext.getPUMP()
        );
    }
}

