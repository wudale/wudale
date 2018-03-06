package com.howtographql.hackernews;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class SigninPayload {
    private final String token;
    private final User user;

    public SigninPayload(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
