package com.kakaopay.kakao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class KakaoApplication {

    /*@PostConstruct
    void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }*/

    public static void main(String[] args) {
        System.out.println("카카오페이 사전과제1 - 손지은");
        SpringApplication.run(KakaoApplication.class, args);
        System.out.println("쿠폰 api 작동 준비 완료!");
    }

}
