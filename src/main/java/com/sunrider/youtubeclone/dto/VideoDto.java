package com.sunrider.youtubeclone.dto;

import com.sunrider.youtubeclone.model.VideoStatus;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class VideoDto {
    private String videoId;
    @NotBlank
    private String userId;
    @NotBlank
    private String videoName;
    @NotBlank
    private String description;
    @Size(min = 1)
    private Set<String> tags;
    private VideoStatus videoStatus;
    @NotBlank
    private String url;
    @NotBlank
    private String thumbnailUrl;
    @Min(value = 0)
    private int likeCount;
    @Min(value = 0)
    private int dislikeCount;
}
