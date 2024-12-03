package az.pashabank.msparnik.context;

import az.pashabank.msparnik.model.dto.DeviceStatus;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DeviceContext {
    private DeviceStatus LED = DeviceStatus.PASSIVE;
    private DeviceStatus PUMP = DeviceStatus.PASSIVE;
    private DeviceStatus MOTOR = DeviceStatus.PASSIVE;
    private DeviceStatus FAN = DeviceStatus.PASSIVE;
}
