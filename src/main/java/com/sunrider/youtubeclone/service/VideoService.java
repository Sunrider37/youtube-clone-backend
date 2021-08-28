package com.sunrider.youtubeclone.service;

import com.sunrider.youtubeclone.dto.CommentDto;
import com.sunrider.youtubeclone.dto.UploadVideoResponse;
import com.sunrider.youtubeclone.dto.VideoDto;
import com.sunrider.youtubeclone.exception.YoutubeException;
import com.sunrider.youtubeclone.mapper.CommentMapper;
import com.sunrider.youtubeclone.mapper.VideoMapper;
import com.sunrider.youtubeclone.model.Comment;
import com.sunrider.youtubeclone.model.Video;
import com.sunrider.youtubeclone.model.VideoStatus;
import com.sunrider.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile file, String userId){
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        video.setUserId(userId);
        videoRepository.save(video);
        return new UploadVideoResponse(video.getId(), videoUrl);
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video video = getVideoById(videoId);
        String url = s3Service.uploadFile(file);
        video.setThumbnailUrl(url);
        videoRepository.save(video);
        return url;
    }

    public List<VideoDto> getAllVideos(){
        return videoRepository.findAll()
                .stream().filter(video -> VideoStatus.PUBLIC.equals(video.getVideoStatus()))
                .map(videoMapper::mapToDto).toList();
    }

    public VideoDto getVideo(String videoId){
        VideoDto videoDto = videoMapper.mapToDto(getVideoById(videoId));
        increaseViewCount(videoDto);
        return videoDto;
    }

    private Video getVideoById(String videoId) {
       return videoRepository.findById(videoId).orElseThrow(() ->
                new YoutubeException("Cannot find video"));
    }

    public Set<VideoDto> getSuggestedVideos(String userId){
        Set<String> likedVideos = userService.getLikedVideos(userId);
        List<Video> likedVideoList = videoRepository.findByIdIn(likedVideos);
        Set<String> tags = likedVideoList.stream()
                .map(Video::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        return videoRepository.findByTagsIn(tags)
                .stream()
                .limit(5)
                .map(videoMapper::mapToDto)
                .collect(Collectors.toSet());
    }


    private void increaseViewCount(VideoDto videoDto) {
        Video video = getVideoById(videoDto.getVideoId());
        video.increaseViewCount();
        videoRepository.save(video);
    }

    public void deleteVideo(String id) {
        String videoUrl = getVideo(id).getUrl();
        s3Service.deleteFile(videoUrl);
        videoRepository.deleteById(id);
    }

    public VideoDto like(String videoId){
        Video video = getVideoById(videoId);
        return null;
    }

    public List<CommentDto> getAllComments(String videoId){
        return videoRepository.findById(videoId).stream()
                .map(video -> commentMapper.mapToDtoList(video.getComments()))
                .findAny().orElse(Collections.emptyList());
    }

    public VideoDto editVideoMetadata(VideoDto videoDtoMetadata) {
        Video video = getVideoById(videoDtoMetadata.getVideoId());
        video.setTitle(videoDtoMetadata.getVideoName());
        video.setDescription(videoDtoMetadata.getDescription());
        video.setVideoUrl(videoDtoMetadata.getUrl());
        video.setTags(videoDtoMetadata.getTags());
        video.setVideoStatus(videoDtoMetadata.getVideoStatus());
        videoRepository.save(video);
        return videoMapper.mapToDto(video);
    }

    public void addComment(CommentDto commentDto, String id) {
        Video video = getVideoById(id);
        Comment comment = commentMapper.mapFromDto(commentDto);
        video.addComment(comment);
        videoRepository.save(video);
    }


    public List<VideoDto> getAllVideosByChannel(String userId) {
        List<Video> videos = videoRepository.findByUserId(userId);
        return videos.stream()
                .map(videoMapper::mapToDto)
                .toList();
    }



    public VideoDto dislike(String videoId) {
        var video = getVideoById(videoId);

        if (userService.ifDislikedVideo(videoId)) {
            video.decreaseDisLikeCount();
            userService.removeFromDislikedVideo(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            video.decreaseLikeCount();
            userService.removeFromLikedVideos(videoId);
        } else {
            video.increaseDisLikeCount();
            userService.addToDislikedVideo(videoId);
        }
        videoRepository.save(video);
        return videoMapper.mapToDto(video);
    }
}
