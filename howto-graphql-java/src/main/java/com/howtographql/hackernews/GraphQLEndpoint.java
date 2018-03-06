package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;

/**
 *
 * <a href="https://www.howtographql.com/graphql-java/0-introduction/">Graphql-java Tutorial</a>
 *
 *
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
    public GraphQLEndpoint() {
        super(buildSchema());
    }

    static final LinkRepository linkRepository;
    static final UserRepository userRepository;

    static {
        MongoDatabase db = new MongoClient().getDatabase("howtographql");
        linkRepository = new LinkRepository(db.getCollection("links"));
        userRepository = new UserRepository(db.getCollection("users"));
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository, userRepository), new Mutation(linkRepository, userRepository), new SigninResolver())
                .build()
                .makeExecutableSchema();
    }

    public static void main(String[] args) {
        User user = new User("wuxiaole" + System.currentTimeMillis(), "wuxiaole0577@gmail.com", "wuxiaole's password");
        user = userRepository.saveUser(user);
        System.out.println(user.getId());

    }
}
