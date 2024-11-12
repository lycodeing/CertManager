package cn.lycodeing.cert.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CertApplication {
    public static void main(String[] args) {
        SpringApplication.run(CertApplication.class, args);
    }
}
