package com.example.Manager.services;

import com.example.Manager.dto.UserDto;
import com.example.Manager.entities.User;
import com.example.Manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User mapToEntity(UserDto userDto) {
        return getUserByUsername(userDto.getUsername());
    }

    public UserDto mapToDto(User user) {
        return new UserDto(user.getUsername());
    }
}
