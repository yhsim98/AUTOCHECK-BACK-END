package com.auto.check.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@MapperScan("com.auto.check.mapper")
@Configuration
public class DatabaseConfig {

    @Bean
    public DataSourceTransactionManager mybatisTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
