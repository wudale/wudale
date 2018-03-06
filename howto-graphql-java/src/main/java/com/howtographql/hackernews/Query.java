package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.*;

import java.util.List;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class Query implements GraphQLQueryResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public Query(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }

    public List<User> allUsers() {
        return userRepository.getAllUsers();
    }
}
