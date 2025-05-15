package com.fbytes.mdcspringintegration.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Random;

@Service
public class Service2 {
    private static final Logger logger = LogManager.getLogger(Service2.class);
    private final Random random = new Random();

    public String process2(Integer src) {
        logger.debug("encoding {}", src);
        byte[] encodedBytes = Base64.getEncoder().encode(String.valueOf(src).getBytes());
        try{
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
        }
        logger.debug("result: {}", encodedBytes);
        return new String(encodedBytes);
    }
}
