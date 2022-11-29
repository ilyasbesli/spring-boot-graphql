package com.communitygaming;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
public class UserGraphqlTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldAddUserAndQueryBack() {
        this.graphQlTester.documentName("user-mutation")
                .execute()
                .path("createUser.firstName")
                .entity(String.class).isEqualTo("ilyas");

        this.graphQlTester.documentName("user-query")
                .variable("id", 1)
                .execute()
                .path("getUser.firstName")
                .entity(String.class).isEqualTo("ilyas");
    }

}
