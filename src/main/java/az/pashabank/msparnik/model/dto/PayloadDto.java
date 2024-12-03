package az.pashabank.msparnik.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PayloadDto {
    private String quantity;
    private BigDecimal value;
}
