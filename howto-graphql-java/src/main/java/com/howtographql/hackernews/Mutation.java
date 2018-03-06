package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.GraphQLException;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class Mutation implements GraphQLMutationResolver {
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public Link createLink(String url, String description) {
        Link newLink = new Link(null, url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }

    public User createUser(String name, AuthData authData) {
        User user = new User(name, authData.getEmail(), authData.getPassword());
        return userRepository.saveUser(user);
    }

    public SigninPayload signinUser(AuthData authData) {
        User user = userRepository.findByEmail(authData.getEmail());
        if(user != null && user.getPassword().equals(authData.getPassword())){
            return new SigninPayload(user.getId(), user);
        }

        throw new GraphQLException("Invalid credentials");
    }
}
