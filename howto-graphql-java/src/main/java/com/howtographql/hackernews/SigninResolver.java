package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public User user(SigninPayload signinPayload) {
        return signinPayload.getUser();
    }

}
