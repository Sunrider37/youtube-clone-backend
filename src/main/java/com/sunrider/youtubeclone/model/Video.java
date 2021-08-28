package com.sunrider.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Video")
public class Video {

    @Id
    private String id;
    private String title;

    private String description;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger dislikes = new AtomicInteger(0);
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<Comment> comments = new ArrayList<>();

    public int likeCount() {
        return likes.get();
    }

    public int disLikeCount() {
        return dislikes.get();
    }

    public void increaseViewCount() {
        viewCount.incrementAndGet();
    }
    public void increaseLikesCount(){
        likes.incrementAndGet();
    }
    public void decreaseLikeCount() {
        likes.decrementAndGet();
    }
    public void increaseDisLikeCount() {
        dislikes.incrementAndGet();
    }

    public void decreaseDisLikeCount() {
        dislikes.decrementAndGet();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
