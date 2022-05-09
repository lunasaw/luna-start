package com.luna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author luna
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"${luna.auto-scan-package}"})
public class LunaStartApplication {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LunaStartApplication.class, args);
    }

}
