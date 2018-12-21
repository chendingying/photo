package com.photo.warehouse.conf;///*
// * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
// *            http://www.xiaomaihd.com
// */
//package com.reported.rarrier.conf;
//
//import com.reported.rarrier.jwt.JsonWebTokenSecurityConfig;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
///**
// * Created by YangFan on 2016/11/28 上午10:30.
// * <p/>
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends JsonWebTokenSecurityConfig {
//    @Override
//    protected void setupAuthorization(HttpSecurity http) throws Exception {
//        http.authorizeRequests().mvcMatchers("/api/**").permitAll()
////
////                // allow anonymous access to /user/login endpoint
////                .antMatchers("/api/auth/jwt/token").permitAll()
////                .antMatchers("/api/auth/jwt/getcode").permitAll()
////                .antMatchers("/api/product/process/excelInport").permitAll()
////                .antMatchers("/api/product/process/ftpDownload").permitAll()
//////                 authenticate all other requests
//                .anyRequest().authenticated();
//    }
//}
