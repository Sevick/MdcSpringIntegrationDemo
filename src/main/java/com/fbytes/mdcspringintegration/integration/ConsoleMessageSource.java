package com.fbytes.mdcspringintegration.integration;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConsoleMessageSource implements MessageSource<Pair<String,String>> {
    private final Scanner scanner = new Scanner(System.in);
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public GenericMessage<Pair<String,String>> receive() {
        Integer id = counter.getAndIncrement();
        System.out.print(MessageFormat.format("#{0} Enter text: ",counter.getAndIncrement()));
        return new GenericMessage<>(Pair.of(String.valueOf(id), scanner.nextLine()));
    }
}

