package com.fbytes.mdcspringintegration.integration;

import com.fbytes.mdcspringintegration.service.MDCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Service;

@Service
public class MdcClearingTaskDecorator implements TaskDecorator {
    private static final Logger logger = LogManager.getLogger(MdcClearingTaskDecorator.class);
    private final MDCService mdcService;

    public MdcClearingTaskDecorator(MDCService mdcService) {
        this.mdcService = mdcService;
    }

    @Override
    public Runnable decorate(Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } finally {
                logger.debug("Cleaning the MDC context");
                mdcService.clearMDC();
            }
        };
    }
}
