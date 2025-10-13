package com.example.Manager.services;

import com.example.Manager.dto.UserDto;
import com.example.Manager.entities.User;
import com.example.Manager.exceptions.UserIsNotUniqueException;
import com.example.Manager.repositories.UserRepository;
import org.hibernate.NonUniqueObjectException;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public synchronized UserDto create(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserIsNotUniqueException("User with username " + userDto.getUsername() + " is already exists!");
        }

        return mapToDto(userRepository.save(user));
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User mapToEntity(UserDto userDto) {
        return getUserByUsername(userDto.getUsername());
    }

    public UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }
}