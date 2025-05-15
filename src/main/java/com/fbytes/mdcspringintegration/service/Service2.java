package com.fbytes.mdcspringintegration.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Service2 {
    private static final Logger logger = LogManager.getLogger(Service2.class);

    public String process2(Integer src) {
        logger.debug("encoding {}", src);
        byte[] encodedBytes = Base64.getEncoder().encode(String.valueOf(src).getBytes());
        logger.debug("result: {}", encodedBytes);
        return new String(encodedBytes);
    }
}
