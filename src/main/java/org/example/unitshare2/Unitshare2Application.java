package org.example.unitshare2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @EntityListener랑 같이 씀, jpa 감사 기능 활성화
public class Unitshare2Application {
    public static void main(String[] args) {
        SpringApplication.run(Unitshare2Application.class, args);
    }
}
