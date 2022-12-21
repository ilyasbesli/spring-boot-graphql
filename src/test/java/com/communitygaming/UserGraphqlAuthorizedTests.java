package com.communitygaming;

import com.communitygaming.dto.AuthToken;
import com.communitygaming.security.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class UserGraphqlAuthorizedTests {

    @Autowired
    private WebGraphQlTester graphQlTester;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setup() {
    }

    @Test
    void userGetWithAuthorized() {
        AuthToken token = tokenProvider.createToken(UsernamePasswordAuthenticationToken
                .authenticated("user@user.com", null, List.of(new SimpleGrantedAuthority("ADMIN"))));

        WebGraphQlTester tester = this.graphQlTester.mutate()
                .headers(headers -> headers.setBearerAuth(token.getJwtToken()))
                .build();

        tester.documentName("user-query")
                .variable("id", "63851c59dc4dfe0d5d74b883")
                .execute()
                .path("getUser.firstName")
                .entity(String.class).isEqualTo("ilyas");
    }
}
