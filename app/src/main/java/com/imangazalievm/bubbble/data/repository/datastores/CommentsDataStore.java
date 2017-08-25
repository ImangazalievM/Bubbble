package com.imangazalievm.bubbble.data.repository.datastores;

import com.imangazalievm.bubbble.data.mappers.CommentResponseMapper;
import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CommentsDataStore {

    private DribbbleApiService dribbbleApiService;
    private CommentResponseMapper commentResponseMapper;

    @Inject
    public CommentsDataStore(DribbbleApiService dribbbleApiService, CommentResponseMapper commentResponseMapper) {
        this.dribbbleApiService = dribbbleApiService;
        this.commentResponseMapper = commentResponseMapper;
    }

    public Single<List<Comment>> getComments(ShotCommentsRequestParams requestParams) {
        return dribbbleApiService.getShotComments(requestParams.getShotId(), requestParams.getPage(), requestParams.getPageSize())
                .map(comments -> commentResponseMapper.map(comments));
    }

}
