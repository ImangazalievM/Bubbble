package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.repository.datastores.CommentsDataStore;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.repository.ICommentsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CommentsRepository implements ICommentsRepository {

    private CommentsDataStore commentsDataStore;

    @Inject
    public CommentsRepository(CommentsDataStore commentsDataStore) {
        this.commentsDataStore = commentsDataStore;
    }

    @Override
    public Single<List<Comment>> getComments(ShotCommentsRequestParams requestParams) {
        return commentsDataStore.getComments(requestParams);
    }

}
