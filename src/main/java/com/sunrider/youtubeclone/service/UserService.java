package com.sunrider.youtubeclone.service;

import com.sunrider.youtubeclone.dto.VideoDto;
import com.sunrider.youtubeclone.exception.YoutubeException;
import com.sunrider.youtubeclone.model.User;
import com.sunrider.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String CANNOT_FIND_USER = "Cannot find user";
    private final UserRepository userRepository;

    public void addVideo(VideoDto videoDto){
        var currentUser = getCurrentUser();
        currentUser.addToVideoHistory(videoDto.getVideoId());
        userRepository.save(currentUser);
    }

    private User getCurrentUser(){
        String sub = ((Jwt) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getClaim("sub");

        return userRepository.findBySub(sub).orElseThrow(() ->
                new YoutubeException(CANNOT_FIND_USER));
    }

    public Set<String> getHistory(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new YoutubeException(CANNOT_FIND_USER));
        return user.getVideoHistory();
    }

    public void addToLikedVideo(String videoId){
        var user = getCurrentUser();
        user.addToLikedVideos(videoId);
        userRepository.save(user);
    }

    public void removeFromLikedVideos(String videoId){
        var user = getCurrentUser();
        user.removeFromLikedVideos(videoId);
        userRepository.save(user);
    }

    public void addToDislikedVideo(String videoId){
        var user =getCurrentUser();
        user.addToDislikedVideo(videoId);
        userRepository.save(user);
    }

    public void removeFromDislikedVideo(String videoId){
        var user = getCurrentUser();
        user.removeFromDislikedVideo(videoId);
        userRepository.save(user);
    }

    public boolean ifLikedVideo(String videoId){
        return getCurrentUser().getLikedVideos().stream()
                .anyMatch(id -> id.equals(videoId));
    }

    public boolean ifDislikedVideo(String videoId){
        return getCurrentUser().
                getDislikedVideos().stream()
                .anyMatch(id -> id.equals(videoId));
    }

    public Set<String> getLikedVideos(String userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new YoutubeException(CANNOT_FIND_USER));
        return user.getLikedVideos();
    }

    public void subscribeUser(String userId){
        var currentUser = getCurrentUser();
        currentUser.addToSubscribedUsers(userId);
        var subscribedToUser = userRepository.findById(userId)
                .orElseThrow();
        subscribedToUser.addToSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(subscribedToUser);
    }

    public void unsubscribeUser(String userId){
        var currentUser = getCurrentUser();
        currentUser.removeFromSubscribedUsers(userId);
        var subscribedToUser = userRepository.findById(userId)
                .orElseThrow();
        subscribedToUser.removeFromSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(subscribedToUser);
    }


}
