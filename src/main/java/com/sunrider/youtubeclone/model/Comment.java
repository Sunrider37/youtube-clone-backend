package com.sunrider.youtubeclone.model;

import lombok.*;

import javax.validation.constraints.Min;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    private String id;
    private String text;
    private String author;
    @Min(value = 0)
    private AtomicInteger likeCount = new AtomicInteger(0);
    @Min(value = 0)
    private AtomicInteger disLikeCount = new AtomicInteger(0);
}
