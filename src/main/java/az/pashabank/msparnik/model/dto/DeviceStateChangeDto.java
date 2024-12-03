package az.pashabank.msparnik.model.dto;

import az.pashabank.msparnik.model.dto.DeviceStatus;
import lombok.Data;

@Data
public class DeviceStateChangeDto {
    private DeviceStatus state;
}
