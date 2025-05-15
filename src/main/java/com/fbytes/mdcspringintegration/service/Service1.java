package com.fbytes.mdcspringintegration.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.Random;

@Service
public class Service1 {
    private static final Logger logger = LogManager.getLogger(Service1.class);
    private final Random random = new Random();

    public Integer process1(String src) {
        logger.debug("process1. src length: {}", src.length());
        Integer cnt = countWords(src);
        try{
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
            // ok
        }
        logger.debug("words processed: {}", cnt);
        return cnt;
    }

    private Integer countWords(String src) {
        if (src == null || src.trim().isEmpty()) {
            return 0;
        }
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(src);

        int wordCount = 0;
        int start = breakIterator.first();
        int end = breakIterator.next();

        while (end != BreakIterator.DONE) {
            if (Character.isLetterOrDigit(src.charAt(start))) {
                wordCount++;
            }
            start = end;
            end = breakIterator.next();
        }
        return wordCount;
    }
}
