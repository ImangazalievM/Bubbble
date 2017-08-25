package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.TeamResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Team;

import javax.inject.Inject;

public class TeamResponseMapper extends Mapper<TeamResponse, Team> {

    @Inject
    public TeamResponseMapper() {
    }

    @Override
    public Team map(TeamResponse teamResponse) {
        return new Team();
    }

}
