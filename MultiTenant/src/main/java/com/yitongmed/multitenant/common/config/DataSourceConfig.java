package com.yitongmed.multitenant.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置类
 */
@Configuration
@Slf4j
public class DataSourceConfig {
    @Bean(name = "primaryDataSource", initMethod = "init")
    @ConfigurationProperties(prefix = "datasource.druid.primary")
    public DruidDataSource createMasterDataSource() throws SQLException {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "tenant1DataSource", initMethod = "init")
    @ConfigurationProperties(prefix = "datasource.druid.tenant1")
    public DruidDataSource createTenant1DataSource() throws SQLException {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    //这里还有一个遗憾，quartz无法动态切换合适的数据源，只能通过MyCat讲数据源聚合，再由应用程序分配
    //因为MyCat还是需要分库的，所以对一些常量要统一处理
    @Bean
    @Primary
    public DataSource createDynamicDataSource(@Qualifier("primaryDataSource") DataSource master, @Qualifier("tenant1DataSource") DataSource tenant1) throws SQLException {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(master);
        Map<Object, Object> map = new HashMap<>();
        map.put("master", master);
        map.put("tenant1", tenant1);
        dynamicDataSource.setTargetDataSources(map);
        log.info("Dynamic data source has been initialized!");
        return dynamicDataSource;
    }
}