package az.pashabank.msparnik.service;

import az.pashabank.msparnik.dao.entity.MainEntity;
import az.pashabank.msparnik.dao.repository.MainRepository;
import az.pashabank.msparnik.model.UnknownDeviceException;
import az.pashabank.msparnik.model.dto.DeviceStatus;
import az.pashabank.msparnik.model.dto.FrontPayloadDto;
import az.pashabank.msparnik.model.dto.PayloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final int FETCH_THRESHOLD = 30;

    private final BigDecimal TEMP_THRESHOLD_LOW = BigDecimal.valueOf(20);
    private final BigDecimal TEMP_THRESHOLD_HIGH = BigDecimal.valueOf(30);

    private final BigDecimal MOISTURE_THRESHOLD = BigDecimal.valueOf(50);

    private final BigDecimal HUMIDITY_THRESHOLD = BigDecimal.valueOf(50);

    private final MainRepository mainRepository;

    public List<DeviceStatus> handleAllPayloads(List<PayloadDto> payloads) {
        var payloadResponses = new ArrayList<DeviceStatus>();

        payloads.forEach(payload -> payloadResponses.addAll(handlePayload(payload)));

        return payloadResponses;
    }

    private List<DeviceStatus> handlePayload(PayloadDto payload) {
        return switch (payload.getQuantity()) {
            case "temperature" -> handleTemperature(payload);
            case "moisture" -> handleMoisture(payload);
            case "humidity" -> handleHumidity(payload);
            default -> throw new UnknownDeviceException("Unknown device: " + payload.getQuantity());
        };
    }

    private List<DeviceStatus> handleTemperature(PayloadDto payload) {
        var tempEntity = MainEntity.builder()
                .device(payload.getQuantity())
                .id(payload.getId())
                .value(payload.getValue())
                .build();

        mainRepository.save(tempEntity);

        if (TEMP_THRESHOLD_LOW.compareTo(payload.getValue()) != -1) {
            return List.of(ACTIVE, PASSIVE);
        } else if (TEMP_THRESHOLD_HIGH.compareTo(payload.getValue()) != 1) {
            return List.of(PASSIVE, ACTIVE);
        } else {
            return List.of(PASSIVE, PASSIVE);
        }
    }

    private List<DeviceStatus> handleMoisture(PayloadDto payload) {
        var moistureEntity = MainEntity.builder()
                .device(payload.getQuantity())
                .id(payload.getId())
                .value(payload.getValue())
                .build();

        mainRepository.save(moistureEntity);

        if (MOISTURE_THRESHOLD.compareTo(payload.getValue()) != -1) {
            return List.of(ACTIVE);
        }
        else return List.of(PASSIVE);
    }

    private List<DeviceStatus> handleHumidity(PayloadDto payload) {
        var humidityEntity = MainEntity.builder()
                .device(payload.getQuantity())
                .id(payload.getId())
                .value(payload.getValue())
                .build();

        mainRepository.save(humidityEntity);

        if (HUMIDITY_THRESHOLD.compareTo(payload.getValue()) != -1) {
            return List.of(ACTIVE);
        } else return List.of(PASSIVE);
    }

    public Map<String, List<FrontPayloadDto>> getData() {
        var temperatureEntities = fetchByDeviceWithThreshold("temperature", FETCH_THRESHOLD);
        var moistureEntities = fetchByDeviceWithThreshold("moisture", FETCH_THRESHOLD);
        var humidityEntities = fetchByDeviceWithThreshold("humidity", FETCH_THRESHOLD);

        var map = new HashMap<String, List<FrontPayloadDto>>();

        map.put("temperature", mapEachDeviceTypeToPayloadDto(temperatureEntities));
        map.put("moisture", mapEachDeviceTypeToPayloadDto(moistureEntities));
        map.put("humidity", mapEachDeviceTypeToPayloadDto(humidityEntities));

        return map;
    }

    private List<FrontPayloadDto> mapEachDeviceTypeToPayloadDto(List<MainEntity> payloadEntities) {
        var payloadDtos = new ArrayList<FrontPayloadDto>();

        payloadEntities.forEach(entity -> payloadDtos.add(FrontPayloadDto.builder()
                        .id(entity.getId())
                        .timestamp(entity.getCreatedAt())
                        .value(entity.getValue())
                .build()));

        return payloadDtos;
    }

    private List<MainEntity> fetchByDeviceWithThreshold(String deviceName, int limit) {
        var pageable = PageRequest.of(0, limit);
        return mainRepository.findByDevice(deviceName, pageable);
    }
}

