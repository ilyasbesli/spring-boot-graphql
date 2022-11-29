package com.communitygaming;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
public class TournamentGraphqlTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldAddTournament() {
        this.graphQlTester.documentName("tournament-mutation")
                .execute()
                .path("createTournament.name")
                .entity(String.class).isEqualTo("New Torunament");

    }

    @Test
    void shouldQueryBackTournament() {
        shouldAddTournament();

        this.graphQlTester.documentName("tournament-query")
                .variable("id", 1)
                .execute()
                .path("getTournament.name")
                .entity(String.class).isEqualTo("New tournament")
                .path("getTournament.creator.id").entity(String.class).isEqualTo("63851c59dc4dfe0d5d74b883");

    }

    @Test
    void shouldUpdateTournament() {
        shouldAddTournament();
        this.graphQlTester.documentName("tournament-mutation")
                .execute()
                .path("updateTournament.name")
                .entity(String.class).isEqualTo("New tournament updated version")
                .path("getTournament.creator.id").entity(String.class).isEqualTo("63851c59dc4dfe0d5d74b883");
    }

}
