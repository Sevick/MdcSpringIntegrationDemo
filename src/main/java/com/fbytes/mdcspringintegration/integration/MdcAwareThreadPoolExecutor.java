package com.fbytes.mdcspringintegration.integration;

import com.fbytes.mdcspringintegration.service.MDCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MdcAwareThreadPoolExecutor extends ThreadPoolTaskExecutor {
    private static final Logger logger = LogManager.getLogger(MdcAwareThreadPoolExecutor.class);

    private final MDCService mdcService;

    public MdcAwareThreadPoolExecutor(MDCService mdcService) {
        super();
        this.mdcService = mdcService;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        //TODO: check if super should be called
        logger.debug("Cleaning the MDC context");
        mdcService.clearMDC();
    }
}