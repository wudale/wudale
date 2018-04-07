package com.example.howtospringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/4/2
 */
@Component
public class DemoExitCodeGenerator implements ExitCodeGenerator, ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(DemoExitCodeGenerator.class);
    private ApplicationContext applicationContext;

    @Override
    public int getExitCode() {

        logger.info("Demo of spring exit hook");
        return 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
