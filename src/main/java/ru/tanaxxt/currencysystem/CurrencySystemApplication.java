package ru.tanaxxt.currencysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencySystemApplication.class, args);
    }

}
