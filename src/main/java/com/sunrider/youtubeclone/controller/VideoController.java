package com.sunrider.youtubeclone.controller;

import com.sunrider.youtubeclone.dto.CommentDto;
import com.sunrider.youtubeclone.dto.UploadVideoResponse;
import com.sunrider.youtubeclone.dto.VideoDto;
import com.sunrider.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<UploadVideoResponse> uploadVideo(@RequestParam("file")MultipartFile file,
                                           @RequestParam("userId") String userId,
                                           UriComponentsBuilder uriComponentsBuilder){
        UploadVideoResponse uploadVideoResponse = videoService.uploadVideo(file, userId);
        var uriComponents = uriComponentsBuilder.path("/{id}")
                .buildAndExpand(uploadVideoResponse.getVideoId());
        return ResponseEntity.created(uriComponents.toUri()).body(uploadVideoResponse);
    }

    @PostMapping("/thumbnail/upload")
    public ResponseEntity<String> uploadThumbnail(
            @RequestParam("file") MultipartFile file,
            @RequestParam("videoId") String videoId,
            UriComponentsBuilder uriComponentsBuilder
    )
    {
        String thumbnailUrl = videoService.uploadThumbnail(file,videoId);
        var uriComponents = uriComponentsBuilder.path("/{id}")
                .buildAndExpand(thumbnailUrl);
        return ResponseEntity.created(uriComponents.toUri()).body(
                "Thumbnail uploaded successfully"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoMetaData(@PathVariable String id){
        return ResponseEntity.ok(videoService.getVideo(id));
    }

    @GetMapping
    public ResponseEntity<List<VideoDto>> getAllVideos(){
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable String id){
        videoService.deleteVideo(id);
        return ResponseEntity.ok("Video deleted");
    }

    @PutMapping
    public VideoDto editVideoMetadata(@RequestBody @Validated VideoDto videoDto){
        return videoService.editVideoMetadata(videoDto);
    }

    @GetMapping("/suggested/")
    public Set<VideoDto> getSuggestedVideos(@PathVariable String userId){
        return videoService.getSuggestedVideos(userId);
    }

    @PostMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComments(@PathVariable String id, @RequestBody CommentDto commentDto) {
        videoService.addComment(commentDto, id);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable String id){
        return ResponseEntity.ok(videoService.getAllComments(id));
    }

    @GetMapping("channel/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> allChannelVideos(@PathVariable String userId) {
        return videoService.getAllVideosByChannel(userId);
    }

    @PostMapping("{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable String id) {
        return videoService.like(id);
    }

    @PostMapping("{id}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto disLikeVideo(@PathVariable String id) {
        return videoService.dislike(id);
    }

}
