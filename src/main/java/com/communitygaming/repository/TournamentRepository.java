package com.communitygaming.repository;

import com.communitygaming.model.Tournament;
import com.communitygaming.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TournamentRepository extends MongoRepository<Tournament, String> {

}