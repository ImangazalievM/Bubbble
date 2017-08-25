package com.imangazalievm.bubbble.data.mappers;

import com.imangazalievm.bubbble.data.network.responses.ImagesResponse;
import com.imangazalievm.bubbble.domain.commons.Mapper;
import com.imangazalievm.bubbble.domain.models.Images;

import javax.inject.Inject;

public class ImagesResponseMapper extends Mapper<ImagesResponse, Images> {

    @Inject
    public ImagesResponseMapper() {
    }

    @Override
    public Images map(ImagesResponse imagesResponse) {
        return new Images(imagesResponse.getHidpi(),
                imagesResponse.getNormal(),
                imagesResponse.getTeaser());
    }

}
