package com.wudale.common.utils;


import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2017/12/16
 */
public class ReflectionUtils {

    Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);
        listeningExecutorService.submit(() -> {
            Thread.sleep(3000L);
            return 2;
        }).addListener();
    }

}
