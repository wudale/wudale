package com.example.howtospringboot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/4/1
 */

@ConfigurationProperties(prefix = "wudale")
public class MyConfigurationProperties {

    private String name;

    private String age = "18";

    private String shouldIgnored = "true";

    private final List<String> stringArray = new ArrayList<>();

    /**
     * 竟然造成了死循环嵌套
     */
    //private final MyConfigurationProperties nested = new MyConfigurationProperties();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getShouldIgnored() {
        return shouldIgnored;
    }

    public void setShouldIgnored(String shouldIgnored) {
        this.shouldIgnored = shouldIgnored;
    }

    public List<String> getStringArray() {
        return stringArray;
    }

}
