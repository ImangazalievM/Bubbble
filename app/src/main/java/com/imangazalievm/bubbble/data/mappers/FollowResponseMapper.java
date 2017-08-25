package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.FollowResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.User;

import javax.inject.Inject;

public class FollowResponseMapper extends Mapper<FollowResponse, Follow> {

    private UserResponseMapper userResponseMapper;

    @Inject
    public FollowResponseMapper(UserResponseMapper userResponseMapper) {
        this.userResponseMapper = userResponseMapper;
    }

    @Override
    public Follow map(FollowResponse followResponse) {
        User user = userResponseMapper.map(followResponse.getUser());
        return new Follow(followResponse.getId(), followResponse.getDateCreated(), user);
    }

}
