package xgraphql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.taobao.xgraphql.ExecutionResult;
import com.taobao.xgraphql.GraphQL;
import com.taobao.xgraphql.annotation.*;
import com.taobao.xgraphql.annotation.injector.NewInstanceContextInjector;
import com.taobao.xgraphql.execution.DataFetchingEnvironment;
import com.taobao.xgraphql.execution.ExecutionStrategy;
import com.taobao.xgraphql.execution.batched.BatchedExecutionStrategy;
import com.taobao.xgraphql.schema.GraphQLObjectType;
import com.taobao.xgraphql.schema.GraphQLSchema;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/4
 */
public class Sample1 {
    @MountAt
    private static class DummyRoot1 {
        @GraphQLField
        private int age1;
        @GraphQLField
        private String name1;
    }

    @MountAt
    private static class DummyRoot2 {
        @GraphQLField
        private int age2;
        @GraphQLField
        @GraphQLScope(value = GraphQLScopeLevel.Globally)
        public String greeting(String to) {
            return "greeting " + to;
        }

        @GraphQLField
        @GraphQLScope(value = GraphQLScopeLevel.Globally)
        public int mockage() {
            return 5;
        }
    }

    public static void main(String[] args) throws Exception {
        ClassPathScanGraphQLSchemaFactory graphQLSchemaFactory =
                new ClassPathScanGraphQLSchemaFactory(
                        Sample1.class.getClassLoader(),
                        new DefaultImplicitMethodParameterValueResolver() {
                            private transient Map<String, String> implicitContext = new HashMap<String, String>() {{
                                put("from", "implicit source");
                            }};

                            @Override
                            public Object resolve(final Object context, final DataFetchingEnvironment dataFetchingEnvironment, final GraphQLFieldMethodParameter parameter) {
                                return implicitContext.get(parameter.getRealName());
                            }
                        },
                        new DefaultExplicitMethodParameterValueResolver(),
                        "xgraphql");
        QueryRoot queryRoot = graphQLSchemaFactory.getQueryRoot("xGraph",
                new NewInstanceContextInjector()).get();
        GraphQLSchema graphQLSchema = queryRoot.getGraphQLSchema();
        System.out.println(graphQLSchema);
        Assert.assertTrue(graphQLSchema.getQueryType() != null);
        Assert.assertEquals("xGraph", graphQLSchema.getQueryType().getName());
        Assert.assertEquals("DummyRoot1", graphQLSchema.getQueryType().getFieldDefinition("DummyRoot1").getName());
        Assert.assertEquals("age1", ((GraphQLObjectType) graphQLSchema.getQueryType().getFieldDefinition("DummyRoot1").getType()).getFieldDefinition("age1").getName());
        Assert.assertEquals("name1", ((GraphQLObjectType) graphQLSchema.getQueryType().getFieldDefinition("DummyRoot1").getType()).getFieldDefinition("name1").getName());
        Assert.assertEquals("DummyRoot2", graphQLSchema.getQueryType().getFieldDefinition("DummyRoot2").getName());
        Assert.assertEquals("age2", ((GraphQLObjectType) graphQLSchema.getQueryType().getFieldDefinition("DummyRoot2").getType()).getFieldDefinition("age2").getName());
        Assert.assertEquals("greeting", ((GraphQLObjectType) graphQLSchema.getQueryType().getFieldDefinition("DummyRoot2").getType()).getFieldDefinition("greeting").getName());

        Object source = queryRoot.getSource();


        GraphQL graphQL = new GraphQL(graphQLSchema,new BatchedExecutionStrategy());

        String queryString =
                "{" +
                        "dummyService{\n " +
                        "   Dummy{" +
                        "           age\n" +
                        "           myage\n" +
                        "           myname\n" +
                        "           hello(name:\"World\")\n" +
                        "           sayHello(target:\"xGraph\")" +
                        "       }" +
                        "   }" +
                        "  __type(name: \"DummyRoot1\") {\n" +
                        "            name" +
                        "            fields {name}" +
                        "}"+
                        "dummy2:DummyRoot2{" +
                        "   greeting:greeting(to:$to)" +
                        "dummy1:DummyRoot1{" +
                        "   greeting:greeting(to:\"globally\")" +
                        "   }" +
                        "  }" +
                        "dummy1:DummyRoot1{" +
                        "   greeting:greeting(to:\"globally\")" +
                        "   ma:mockage" +
                        "   }" +
                        "}";
        ExecutionResult executionResult = graphQL.execute(queryString, args);

        String jsonStr = JSON.toJSONString(executionResult, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(jsonStr);

    }
}
