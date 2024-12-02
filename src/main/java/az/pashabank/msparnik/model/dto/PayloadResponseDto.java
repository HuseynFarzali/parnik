package az.pashabank.msparnik.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayloadResponseDto {
    private String device;
    private Long deviceId;
    private DeviceStatus status;
}
