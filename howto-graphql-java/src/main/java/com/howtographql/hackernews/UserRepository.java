package com.howtographql.hackernews;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class UserRepository {

    private final MongoCollection<Document> users;

    public UserRepository(MongoCollection<Document> users){
        this.users = users;
    }

    public User findByEmail(String email) {
        Document doc = users.find(eq("email", email)).first();
        return user(doc);
    }

    public User saveUser(User user) {
        Document doc = new Document();
        doc.append("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        users.insertOne(doc);
        return new User(doc.get("_id").toString(),
                user.getName(),
                user.getEmail(),
                user.getPassword());
    }

    private User user(Document doc) {
        return new User(doc.get("_id").toString(),
                doc.getString("name"),
                doc.getString("email"),
                doc.getString("password"));
    }


    public List<User> getAllUsers() {
        List<User> allUsers = Lists.newArrayList();
        for (Document document : users.find()) {
            allUsers.add(user(document));
        }
        return allUsers;
    }
}
