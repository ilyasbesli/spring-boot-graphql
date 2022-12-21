package com.communitygaming;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureGraphQlTester
public class UserGraphqlTests {

    @Autowired
    private MockMvc mockMvc;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        this.webTestClient = MockMvcWebTestClient
                .bindTo(mockMvc)
                .defaultHeader("X-user", "42")
                .filter(logRequest())
                .build();
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnListOfUsersForAuthenticatedRequests() {
        HttpGraphQlTester tester = HttpGraphQlTester.create(webTestClient);
        tester.document("user-query.graphql")
                .variable("id", 1)
                .execute()
                .path("getUser.firstName")
                .entity(String.class).isEqualTo("ilyas");
    }


    @Test
    void shouldAddUserAndQueryBack() {
        /*this.graphQlTester.documentName("user-mutation")
                .execute()
                .path("createUser.firstName")
                .entity(String.class).isEqualTo("ilyas");

        this.graphQlTester.documentName("user-query")
                .variable("id", 1)
                .execute()
                .path("getUser.firstName")
                .entity(String.class).isEqualTo("ilyas");*/
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            System.out.printf("Request: %s %s %n", clientRequest.method(), clientRequest.url());
            return next.exchange(clientRequest);
        };
    }
}
