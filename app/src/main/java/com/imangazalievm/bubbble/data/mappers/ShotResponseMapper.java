package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.ShotResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Images;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.Team;
import com.imangazalievm.bubbble.domain.models.User;

import javax.inject.Inject;

public class ShotResponseMapper extends Mapper<ShotResponse, Shot> {

    private ImagesResponseMapper imagesResponseMapper;
    private UserResponseMapper userResponseMapper;
    private TeamResponseMapper teamResponseMapper;

    @Inject
    public ShotResponseMapper(ImagesResponseMapper imagesResponseMapper,
                              UserResponseMapper userResponseMapper,
                              TeamResponseMapper teamResponseMapper) {
        this.imagesResponseMapper = imagesResponseMapper;
        this.userResponseMapper = userResponseMapper;
        this.teamResponseMapper = teamResponseMapper;
    }

    @Override
    public Shot map(ShotResponse shotResponse) {
        Images images = imagesResponseMapper.map(shotResponse.getImages());
        User user = shotResponse.getUser() != null ? userResponseMapper.map(shotResponse.getUser()) : null;
        Team team = teamResponseMapper.map(shotResponse.getTeam());
        return new Shot(shotResponse.getId(),
                shotResponse.getTitle(),
                shotResponse.getDescription(),
                shotResponse.getWidth(),
                shotResponse.getHeight(),
                images,
                shotResponse.getViewsCount(),
                shotResponse.getLikesCount(),
                shotResponse.getBucketsCount(),
                shotResponse.getCommentsCount(),
                shotResponse.getCreatedAt(),
                shotResponse.getUpdatedAt(),
                shotResponse.getHtmlUrl(),
                shotResponse.getReboundSourceUrl(),
                shotResponse.getTags(),
                user,
                team,
                shotResponse.isAnimated());
    }

}
