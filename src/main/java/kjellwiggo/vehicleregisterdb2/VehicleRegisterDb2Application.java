package kjellwiggo.vehicleregisterdb2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class VehicleRegisterDb2Application {

    public static void main(String[] args) {
        SpringApplication.run(VehicleRegisterDb2Application.class, args);
    }

}
