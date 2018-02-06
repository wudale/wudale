package com.wudale.common.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.annotation.*;
import java.util.Set;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2017/12/16
 */
public class TestReflectionUtils {


    public static void main(String[] args) {
        ((LoggerContext)LoggerFactory.getILoggerFactory()).getStatusManager().add(new OnConsoleStatusListener());
        Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .addUrls(ClasspathHelper.forPackage("utils"))
            .addScanners(new TypeElementsScanner(), new SubTypesScanner(), new TypeAnnotationsScanner()));
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(TestAnnotation.class);
        logger.debug("types: {}", types);

        MDC.put("test", "wudale");


    }



    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    private @interface TestAnnotation {
        String value() default "";
    }


    private static class TestBaseClass {
        protected String strField;

        public String getStrField() {
            return strField;
        }

        public void setStrField(String strField) {
            this.strField = strField;
        }
    }

    @TestAnnotation("Hello World")
    private static class TestClass extends TestBaseClass {
        protected Integer intField;

        public Integer getIntField() {
            return intField;
        }

        public void setIntField(Integer intField) {
            this.intField = intField;
        }
    }
}
