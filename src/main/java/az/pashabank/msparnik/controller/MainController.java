package az.pashabank.msparnik.controller;

import az.pashabank.msparnik.model.dto.DeviceStateChangeDto;
import az.pashabank.msparnik.model.dto.DeviceStatus;
import az.pashabank.msparnik.model.dto.PayloadDto;
import az.pashabank.msparnik.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/internal/data")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @PostMapping
    @ResponseStatus(OK)
    public List<DeviceStatus> postData(@RequestBody List<PayloadDto> payloads) {
        return mainService.handleAllPayloads(payloads);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<DeviceStatus> getDeviceStatuses() {
        return mainService.getDeviceStatuses();
    }

    @GetMapping("/sensor")
    @ResponseStatus(OK)
    public Map<String, BigDecimal> getSensorData() {
        return mainService.getSensorData();
    }

    @GetMapping("/device")
    @ResponseStatus(OK)
    public Map<String, DeviceStatus> getDeviceData() {
        return mainService.getDeviceData();
    }

    @GetMapping("/{deviceName}/{state}")
    @ResponseStatus(OK)
    public void changeDeviceState(@PathVariable String deviceName, @PathVariable DeviceStatus state) {
        mainService.changeDeviceState(deviceName, DeviceStateChangeDto.builder().state(state).build());
    }
}

