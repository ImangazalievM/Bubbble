package com.imangazalievm.bubbble.domain.usecases;

import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.repository.ICommentsRepository;
import com.imangazalievm.bubbble.domain.usecases.base.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetShotCommentsUseCase extends SingleUseCase<ShotCommentsRequestParams, List<Comment>> {

    private ICommentsRepository commentsRepository;

    @Inject
    public GetShotCommentsUseCase(@JobScheduler Scheduler jobScheduler,
                                  @UiScheduler Scheduler uiScheduler,
                                  ICommentsRepository commentsRepository) {
        super(jobScheduler, uiScheduler);

        this.commentsRepository = commentsRepository;
    }

    @Override
    protected Single<List<Comment>> buildSingle(ShotCommentsRequestParams requestParams) {
        return commentsRepository.getComments(requestParams);
    }


}
