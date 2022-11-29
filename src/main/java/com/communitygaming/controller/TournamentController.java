package com.communitygaming.controller;

import com.communitygaming.model.Tournament;
import com.communitygaming.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @QueryMapping(value = "getTournament")
    public Optional<Tournament> getTournament(@Argument(name = "tournamentId") String tournamentId) {
        return tournamentService.findById(tournamentId);
    }

    @MutationMapping(name = "createTournament")
    public Tournament createTournament(@Argument(name = "name") String name, @Argument(name = "userId") String userId) {
        return tournamentService.createTournament(name, userId);
    }

    @MutationMapping(name = "updateTournament")
    public Tournament updateTournament(@Argument(name = "tournamentId") String tournamentId, @Argument(name = "name") String name) {
        return tournamentService.updateTournament(tournamentId, name);
    }

    @MutationMapping(name = "joinTournament")
    public Tournament joinTournament(@Argument(name = "tournamentId") String tournamentId, @Argument(name = "name") String name) {
        return tournamentService.joinTournament(tournamentId, name);
    }
}
