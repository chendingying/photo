package com.photo.warehouse;

import com.photo.warehouse.conf.UserAuthConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.io.IOException;

/**
 * Created by CDZ on 2018/12/7.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.photo.warehouse.mapper")
@EnableScheduling //必须加此注解
//重点
@ServletComponentScan
public class ApplicationSpringBoot extends WebMvcConfigurerAdapter {
//    private static Logger logger = LoggerFactory.getLogger(ApplicationSpringBoot.class);

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(ApplicationSpringBoot.class);
    /**
     */
    public static void main(String[] args) throws IOException {
        SpringApplication.run(ApplicationSpringBoot.class, args);
        logger.debug("启动成功");
    }
    /**
     * 跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedMethods("DELETE", "POST", "GET", "PUT")
                .allowedOrigins("*");
    }

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }
}

