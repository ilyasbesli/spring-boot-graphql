package com.communitygaming.service;

import com.communitygaming.exception.TournamentAlreadyJoinedException;
import com.communitygaming.exception.TournamentNotFoundException;
import com.communitygaming.exception.UserNotAuthorizedException;
import com.communitygaming.exception.UserNotFoundException;
import com.communitygaming.model.Tournament;
import com.communitygaming.repository.TournamentRepository;
import com.communitygaming.util.SecurityUtils;
import graphql.com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Component("tournamentService")
@AllArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserService userService;

    @Override
    public Tournament createTournament(String name, String userId) {
        return tournamentRepository.save(Tournament.builder()
                .name(name)
                .creator(userService.findById(userId).orElseThrow(UserNotFoundException::new))
                .build());
    }

    @Override
    public Optional<Tournament> findById(String tournamentId) {
        return tournamentRepository.findById(tournamentId);
    }

    @Override
    public Tournament updateTournament(String tournamentId, String name) {
        return findById(tournamentId)
                .map(tournament -> {
                    if(!tournament.getCreator().getId().equals(SecurityUtils.getCurrentUserId()))
                        throw new UserNotAuthorizedException();
                    tournament.setName(name);
                    return tournamentRepository.save(tournament);
                })
                .orElseThrow(TournamentNotFoundException::new);
    }

    @Override
    public Tournament joinTournament(String tournamentId, String userId) {
        return findById(tournamentId)
                .filter(tournament -> CollectionUtils.isEmpty(tournament.getParticipants())
                        || (CollectionUtils.isEmpty(tournament.getParticipants())
                            && tournament.getParticipants().stream().noneMatch(participant ->participant.getId().equals(userId)))
                )
                .map(tournament -> {
                    if(CollectionUtils.isEmpty(tournament.getParticipants())) {
                        tournament.setParticipants(Lists.newArrayList());
                    }
                    tournament.getParticipants().add(userService.findById(userId).orElseThrow(UserNotFoundException::new));
                    return tournamentRepository.save(tournament);
                })
                .orElseThrow(TournamentAlreadyJoinedException::new);
    }
}
