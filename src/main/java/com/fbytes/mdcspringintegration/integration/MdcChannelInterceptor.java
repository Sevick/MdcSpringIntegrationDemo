package com.fbytes.mdcspringintegration.integration;

import com.fbytes.mdcspringintegration.service.MDCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
@GlobalChannelInterceptor(patterns = {"*-Q"})
public class MdcChannelInterceptor implements ChannelInterceptor {
    private static final Logger logger = LogManager.getLogger(MdcChannelInterceptor.class);

    @Value("${mdcspringintegration.mdc_header}")
    private String mdcHeader;

    @Autowired
    private MDCService mdcService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        if (!message.getHeaders().containsKey(mdcHeader)) {
            return MessageBuilder.fromMessage(message)
                    .setHeader(mdcHeader, mdcService.fetch(mdcHeader)) // Add a new header
                    .build();
        }
        if (channel instanceof PollableChannel) {
            logger.trace("Cleaning the MDC context before PollableChannel");
            mdcService.clearMDC();  // clear MDC in producer's thread
        }
        return message;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        if (channel instanceof PollableChannel) {
            logger.trace("Restore MDC context after PollableChannel");
            Map<String, String> mdcMap = message.getHeaders().entrySet().stream()
                    .filter(entry -> entry.getKey().equals(mdcHeader))
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> (String) entry.getValue()));
            mdcService.setMDC(mdcMap);
        }
        return message;
    }
}
