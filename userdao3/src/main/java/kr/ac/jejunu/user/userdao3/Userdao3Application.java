package kr.ac.jejunu.user.userdao3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Userdao3Application {

    public static void main(String[] args) {
        SpringApplication.run(Userdao3Application.class, args);
    }

}
