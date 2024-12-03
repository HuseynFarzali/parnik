package az.pashabank.msparnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MsParnikApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsParnikApplication.class, args);
    }

}
