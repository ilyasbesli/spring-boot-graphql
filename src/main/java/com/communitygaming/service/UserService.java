package com.communitygaming.service;

import com.communitygaming.dto.UserDto;
import com.communitygaming.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(UserDto userDto);

    Optional<User> findById(String userId);
}
