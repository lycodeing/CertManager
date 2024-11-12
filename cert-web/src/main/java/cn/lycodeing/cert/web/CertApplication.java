package cn.lycodeing.cert.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "cn.lycodeing.cert.web.mapper")
@SpringBootApplication
public class CertApplication {
    public static void main(String[] args) {
        SpringApplication.run(CertApplication.class, args);
    }
}
