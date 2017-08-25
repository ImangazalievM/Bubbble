package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.CommentResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.User;

import javax.inject.Inject;

public class CommentResponseMapper extends Mapper<CommentResponse, Comment> {

    private UserResponseMapper userResponseMapper;

    @Inject
    public CommentResponseMapper(UserResponseMapper userResponseMapper) {
        this.userResponseMapper = userResponseMapper;
    }

    @Override
    public Comment map(CommentResponse commentResponse) {
        User user = userResponseMapper.map(commentResponse.getUser());
        return new Comment(commentResponse.getId(),
                user,
                commentResponse.getBody(),
                commentResponse.getCreatedAt(),
                commentResponse.getUpdatedAt(),
                commentResponse.getLikesUrl(),
                commentResponse.getLikesCount());
    }

}
