package com.board.boot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//리스너 사용을 위한 설정
@EnableJpaAuditing
public class Boot3Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot3Application.class, args);
    }

}
