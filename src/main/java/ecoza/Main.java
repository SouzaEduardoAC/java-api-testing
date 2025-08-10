package ecoza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SampleApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApiServiceApplication.class, args);
    }
}