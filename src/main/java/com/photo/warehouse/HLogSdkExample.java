package com.photo.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/20.
 */
public class HLogSdkExample {
    private static final Logger logger = LoggerFactory.getLogger(HLogSdkExample.class);
    public static void main(String[] args) {
        logger.info("logback 成功了");
        logger.error("logback 成功了");
    }
}
