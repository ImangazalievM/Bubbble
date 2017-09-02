package com.imangazalievm.bubbble.domain.repository;


import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface CommentsRepository {

    Single<List<Comment>> getComments(ShotCommentsRequestParams requestParams);

}
