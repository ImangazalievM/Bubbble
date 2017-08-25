package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.UserResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Links;
import com.imangazalievm.bubbble.domain.models.User;

import javax.inject.Inject;

public class UserResponseMapper extends Mapper<UserResponse, User> {

    private LinksResponseMapper linksResponseMapper;

    @Inject
    public UserResponseMapper(LinksResponseMapper linksResponseMapper) {
        this.linksResponseMapper = linksResponseMapper;
    }

    @Override
    public User map(UserResponse userResponse) {
        Links links = linksResponseMapper.map(userResponse.getLinks());
        return new User(userResponse.getId(),
                userResponse.getName(),
                userResponse.getUsername(),
                userResponse.getHtmlUrl(),
                userResponse.getAvatarUrl(),
                userResponse.getBio(),
                userResponse.getLocation(),
                links,
                userResponse.getBucketsCount(),
                userResponse.getCommentsReceivedCount(),
                userResponse.getFollowersCount(),
                userResponse.getFollowingsCount(),
                userResponse.getLikesCount(),
                userResponse.getLikesReceivedCount(),
                userResponse.getProjectsCount(),
                userResponse.getReboundsReceivedCount(),
                userResponse.getShotsCount(),
                userResponse.getTeamsCount(),
                userResponse.getCanUploadShot(),
                userResponse.getType(),
                userResponse.isPro(),
                userResponse.getBucketsUrl(),
                userResponse.getFollowersUrl(),
                userResponse.getFollowingUrl(),
                userResponse.getLikesUrl(),
                userResponse.getShotsUrl(),
                userResponse.getTeamsUrl(),
                userResponse.getCreatedAt(),
                userResponse.getUpdatedAt());
    }

}
