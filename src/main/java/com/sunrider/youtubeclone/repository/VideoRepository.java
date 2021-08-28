package com.sunrider.youtubeclone.repository;

import com.sunrider.youtubeclone.dto.VideoDto;
import com.sunrider.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VideoRepository extends MongoRepository<Video,String> {
    List<Video> findByIdIn(Set<String> likedVideos);


    Optional<Video> findByTagsIn(Set<String> tags);

    List<Video> findByUserId(String userId);
}
