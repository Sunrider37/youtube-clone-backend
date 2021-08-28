package com.sunrider.youtubeclone.mapper;

import com.sunrider.youtubeclone.dto.CommentDto;
import com.sunrider.youtubeclone.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentMapper {

    public Comment mapFromDto(CommentDto commentDto){
        return Comment.builder()
                .text(commentDto.getCommentText())
                .author(commentDto.getCommentAuthor())
                .build();
    }

    public List<CommentDto> mapToDtoList(List<Comment> comments){
        return comments.stream()
                .map(this::mapToDto).toList();
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .commentAuthor(comment.getAuthor())
                .commentText(comment.getText())
                .likeCount(comment.getLikeCount().get())
                .dislikeCount(comment.getDisLikeCount().get())
                .build();
    }
}
