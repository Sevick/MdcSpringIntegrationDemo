package com.fbytes.mdcspringintegration.integration;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConsoleMessageSource implements MessageSource<Pair<String, String>> {
    private final Scanner scanner = new Scanner(System.in);
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public GenericMessage<Pair<String, String>> receive() {
        Integer id = counter.getAndIncrement();
        try {
            String inStr = scanner.nextLine();
            return new GenericMessage<>(Pair.of(String.valueOf(id), inStr));
        } catch (Exception e) {
            return null;
        }
    }
}

