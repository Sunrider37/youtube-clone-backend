package com.sunrider.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(value = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String sub;
    private String picture;

    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    private Set<String> videoHistory;
    private Set<String> likedVideos;
    private Set<String> dislikedVideos;

    public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void addToVideoHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void addToSubscribedUsers(String userId) {
        subscribedToUsers.add(userId);
    }

    public void addToSubscribers(String id) {
        subscribers.add(id);
    }

    public void removeFromLikedVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void addToDislikedVideo(String videoId) {
        dislikedVideos.add(videoId);
    }

    public void removeFromDislikedVideo(String videoId) {
        dislikedVideos.remove(videoId);
    }

    public void removeFromSubscribedUsers(String userId) {
        subscribedToUsers.remove(userId);
    }

    public void removeFromSubscribers(String id) {
        subscribers.remove(id);
    }
}
