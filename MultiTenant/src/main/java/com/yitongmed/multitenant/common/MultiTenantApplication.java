package com.yitongmed.multitenant.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.connection.AbstractRoutingConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.yitongmed.multitenant"}, exclude = {MultipartAutoConfiguration.class, DataSourceAutoConfiguration.class, QuartzAutoConfiguration.class})
@MapperScan({"com.yitongmed.*.common.mybatis"})
public class MultiTenantApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiTenantApplication.class, args);
    }
}
