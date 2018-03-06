package com.howtographql.hackernews;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class MongoMain {

    static final LinkRepository linkRepository;
    static final UserRepository userRepository;

    static {
        MongoDatabase db = new MongoClient().getDatabase("howtographql");
        linkRepository = new LinkRepository(db.getCollection("links"));
        userRepository = new UserRepository(db.getCollection("users"));
    }

    public static void main(String[] args) {
        User user = new User("wuxiaole" + System.currentTimeMillis(), "wuxiaole0577@gmail.com", "wuxiaole's password");
        user = userRepository.saveUser(user);
        System.out.println(user.getId());

    }
}
