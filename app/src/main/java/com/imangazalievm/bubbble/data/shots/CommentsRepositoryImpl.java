package com.imangazalievm.bubbble.data.shots;

import com.imangazalievm.bubbble.data.global.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.global.models.Comment;
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.global.repositories.CommentsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CommentsRepositoryImpl implements CommentsRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public CommentsRepositoryImpl(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    public Single<List<Comment>> getComments(ShotCommentsRequestParams requestParams) {
        return dribbbleApiService.getShotComments(requestParams.getShotId(), requestParams.getPage(), requestParams.getPageSize());
    }

}
