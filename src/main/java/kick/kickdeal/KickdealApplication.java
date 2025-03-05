package kick.kickdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KickdealApplication {

    public static void main(String[] args) {
        SpringApplication.run(KickdealApplication.class, args);
    }

}
