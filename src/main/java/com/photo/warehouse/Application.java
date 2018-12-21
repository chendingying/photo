//package com.photo.warehouse;
//
//import com.photo.warehouse.conf.UserAuthConfig;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ImportResource;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
///**
// * Created by CDZ on 2018/11/22.
// */
//@SpringBootApplication
//@EnableScheduling //必须加此注解
//@MapperScan(basePackages = "com.photo.warehouse")
////重点
//@ServletComponentScan
//@ImportResource(locations= {"classpath:*.xml"})
//public class Application extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }
//
//    @Bean
//    UserAuthConfig getUserAuthConfig(){
//        return new UserAuthConfig();
//    }
//
//}