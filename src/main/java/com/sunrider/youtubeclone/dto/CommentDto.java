package com.sunrider.youtubeclone.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    @NotBlank
    private String commentText;

    @NotBlank
    private String commentAuthor;
    @Min(value = 0)
    private int likeCount;

    @Min(value = 0)
    private int dislikeCount;
}
