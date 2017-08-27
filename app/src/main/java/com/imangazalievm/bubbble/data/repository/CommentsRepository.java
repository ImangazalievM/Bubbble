package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.repository.ICommentsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CommentsRepository implements ICommentsRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public CommentsRepository(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    public Single<List<Comment>> getComments(ShotCommentsRequestParams requestParams) {
        return dribbbleApiService.getShotComments(requestParams.getShotId(), requestParams.getPage(), requestParams.getPageSize());
    }

}
