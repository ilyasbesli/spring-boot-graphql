package com.communitygaming.service;

import com.communitygaming.model.Tournament;

import java.util.Optional;

public interface TournamentService {
    Tournament createTournament(String name, String userId);

    Optional<Tournament> findById(String id);

    Tournament updateTournament(String tournamentId, String name);

    Tournament joinTournament(String tournamentId, String userId);
}
