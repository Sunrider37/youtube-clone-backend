package com.sunrider.youtubeclone.mapper;

import com.sunrider.youtubeclone.dto.VideoDto;
import com.sunrider.youtubeclone.model.Video;
import org.springframework.stereotype.Service;

@Service
public class VideoMapper {
    public VideoDto mapToDto(Video video){
        return VideoDto.builder()
                .videoId(video.getId())
                .url(video.getVideoUrl())
                .description(video.getDescription())
                .tags(video.getTags())
                .videoName(video.getTitle())
                .videoStatus(video.getVideoStatus())
                .thumbnailUrl(video.getThumbnailUrl())
                .likeCount(video.getLikes().get())
                .dislikeCount(video.getDislikes().get())
                .build();
    }
}
