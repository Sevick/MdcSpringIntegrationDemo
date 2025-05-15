package com.fbytes.mdcspringintegration.integration;

import com.fbytes.mdcspringintegration.service.MDCService;
import com.fbytes.mdcspringintegration.service.Service1;
import com.fbytes.mdcspringintegration.service.Service2;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class IntegrationConfiguration {
    private static final Logger logger = LogManager.getLogger(IntegrationConfiguration.class);

    @Value("${mdcspringintegration.mdc_header}")
    private String mdcHeader;

    @Autowired
    private ConsoleMessageSource consoleMessageSource;
    @Autowired
    private MDCService mdcService;

    @Autowired
    private Service1 service1;
    @Autowired
    private Service2 service2;

    @Bean
    public MessageChannel queueChannel() {
        return new QueueChannel();
    }

    @Bean(name = "sourcePollerTaskExecutor")
    public TaskExecutor sourcePollerTaskExecutor() {
        ThreadPoolTaskExecutor executor = new MdcAwareThreadPoolExecutor(mdcService);
        executor.initialize();
        return executor;
    }

    @Bean(name = "queuePollerTaskExecutor")
    public TaskExecutor queuePollerTaskExecutor() {
        ThreadPoolTaskExecutor executor = new MdcAwareThreadPoolExecutor(mdcService);
        executor.initialize();
        return executor;
    }


    @Bean
    public org.springframework.integration.dsl.IntegrationFlow demoWorkflow() {

        return org.springframework.integration.dsl.IntegrationFlow
                .from(consoleMessageSource,
                        c -> c.poller(p -> p.fixedDelay(3000).taskExecutor(sourcePollerTaskExecutor()))) // Poll every 3 sec
                .handle((payload, headers) -> {
                    mdcService.setMDC(mdcHeader, ((Pair<String,String>) payload).getKey());
                    return payload;
                })
                .transform(Pair.class, pair -> pair.getValue())
                .filter((str -> !((String) str).isEmpty()))
                .handle(service1, "process1")
                .channel(queueChannel())
                .handle(service2, "process2",
                        c -> c.poller(p -> p.fixedDelay(5000).taskExecutor(queuePollerTaskExecutor()))) // Poll every 5 sec
                .handle((m) -> {
                    logger.info("Result: {}", m.getPayload());
                })
                .get();
    }
}
