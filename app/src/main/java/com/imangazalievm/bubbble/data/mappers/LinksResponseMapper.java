package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.ImagesResponse;
import com.imangazalievm.bubbble.data.network.responses.LinksResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Images;
import com.imangazalievm.bubbble.domain.models.Links;

import javax.inject.Inject;

public class LinksResponseMapper extends Mapper<LinksResponse, Links> {

    @Inject
    public LinksResponseMapper() {
    }

    @Override
    public Links map(LinksResponse linksResponse) {
        return new Links(linksResponse.getWeb(),
                linksResponse.getTwitter());
    }

}
