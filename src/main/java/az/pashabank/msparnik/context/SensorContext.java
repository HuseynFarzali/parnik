package az.pashabank.msparnik.context;

import az.pashabank.msparnik.model.dto.DeviceStatus;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class SensorContext {
    private BigDecimal TEMPERATURE = BigDecimal.ZERO;
    private BigDecimal HUMIDITY = BigDecimal.ZERO;
    private BigDecimal MOISTURE = BigDecimal.ZERO;

    public final BigDecimal TEMP_THRESHOLD_LOW = BigDecimal.valueOf(20);
    public final BigDecimal TEMP_THRESHOLD_HIGH = BigDecimal.valueOf(30);
    public final BigDecimal MOISTURE_THRESHOLD = BigDecimal.valueOf(50);
    public final BigDecimal HUMIDITY_THRESHOLD = BigDecimal.valueOf(50);
}

