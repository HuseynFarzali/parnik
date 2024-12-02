package az.pashabank.msparnik.controller;

import az.pashabank.msparnik.model.dto.DeviceStatus;
import az.pashabank.msparnik.model.dto.FrontPayloadDto;
import az.pashabank.msparnik.model.dto.PayloadDto;
import az.pashabank.msparnik.model.dto.PayloadResponseDto;
import az.pashabank.msparnik.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/internal/data")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @PostMapping
    @ResponseStatus(OK)
    public List<DeviceStatus> postDataWithResponse(@RequestBody List<PayloadDto> payloads) {
        return mainService.handleAllPayloads(payloads);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Map<String, List<FrontPayloadDto>> getData() {
        return mainService.getData();
    }
}
