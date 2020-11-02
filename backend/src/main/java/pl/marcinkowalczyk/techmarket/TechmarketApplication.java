package pl.marcinkowalczyk.techmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechmarketApplication.class, args);
    }

}
