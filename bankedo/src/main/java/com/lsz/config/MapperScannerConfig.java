package com.lsz.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis映射文件扫描配置
 *
 */
@Configuration
@AutoConfigureAfter(DatabaseConfig.class)
public class MapperScannerConfig {

    @Bean
    public MapperScannerConfigurer db1MapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        // 设置自动扫描包, 该包下的Mapper(Dao)将会被mybatis自动注册, 不用写实现类
        configurer.setBasePackage("com.lsz.mapper");
        configurer.setSqlSessionFactoryBeanName("db1SqlSessionFactory");
        return configurer;
    }

    @Bean
    public MapperScannerConfigurer db2MapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        // 设置自动扫描包, 该包下的Mapper(Dao)将会被mybatis自动注册, 不用写实现类
        configurer.setBasePackage("com.lsz.mapper.db2");
        configurer.setSqlSessionFactoryBeanName("db2SqlSessionFactory");
        return configurer;
    }

   
}
