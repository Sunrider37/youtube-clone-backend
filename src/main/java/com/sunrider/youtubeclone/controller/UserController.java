package com.sunrider.youtubeclone.controller;

import com.sunrider.youtubeclone.dto.UserInfoDto;
import com.sunrider.youtubeclone.service.UserRegistrationService;
import com.sunrider.youtubeclone.service.UserService;
import com.sunrider.youtubeclone.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidationService userValidationService;
    private final UserRegistrationService userRegistrationService;

    @GetMapping("/validate")
    public UserInfoDto registerUser(HttpServletRequest httpServletRequest){
        UserInfoDto userInfoDto = userValidationService.validate(httpServletRequest.getHeader(
                "Authorization"
        ));
        userRegistrationService.register(userInfoDto);
        return userInfoDto;
    }

    @GetMapping("/{id}/history")
    public Set<String> userHistory(@PathVariable String id){
        return userService.getHistory(id);
    }

    @PostMapping("/subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void subscribeUser(@PathVariable String userId){
        userService.subscribeUser(userId);
    }

}
