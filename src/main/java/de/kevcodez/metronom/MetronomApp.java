package de.kevcodez.metronom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetronomApp {

    public static void main(String[] args) {
        SpringApplication.run(MetronomApp.class, args);
    }
}
