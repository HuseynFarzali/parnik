package az.pashabank.msparnik.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class FrontPayloadDto {
    private String device;
    private Long id;
    private BigDecimal value;
    
    private LocalDateTime timestamp;
}
