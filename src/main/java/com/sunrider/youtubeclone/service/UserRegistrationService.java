package com.sunrider.youtubeclone.service;

import com.sunrider.youtubeclone.dto.UserInfoDto;
import com.sunrider.youtubeclone.model.User;
import com.sunrider.youtubeclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;

    public void register(UserInfoDto userInfoDto) {
        Optional<User> existingUser = userRepository.findByEmailAddress(userInfoDto.getEmail());
        existingUser.ifPresent(user -> userInfoDto.setId(user.getId()));

        User user = new User();
        user.setSub(userInfoDto.getSub());
        user.setEmailAddress(userInfoDto.getEmail());
        user.setFirstName(userInfoDto.getGivenName());
        user.setLastName(userInfoDto.getFamilyName());
        user.setFullName(userInfoDto.getName());
        user.setPicture(userInfoDto.getPicture());
        userRepository.save(user);
    }
}
