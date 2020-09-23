package com.imangazalievm.bubbble.domain.shotdetails;


import com.imangazalievm.bubbble.domain.global.models.Comment;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.global.repositories.CommentsRepository;
import com.imangazalievm.bubbble.domain.global.repositories.ImagesRepository;
import com.imangazalievm.bubbble.domain.global.repositories.ShotsRepository;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ShotDetailsInteractor {

    private ShotsRepository shotsRepository;
    private CommentsRepository commentsRepository;
    private ImagesRepository imagesRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ShotDetailsInteractor(
            ShotsRepository shotsRepository,
            CommentsRepository commentsRepository,
            ImagesRepository imagesRepository,
            SchedulersProvider schedulersProvider
    ) {
        this.shotsRepository = shotsRepository;
        this.commentsRepository = commentsRepository;
        this.imagesRepository = imagesRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<Shot> getShot(long shotId) {
        return shotsRepository.getShot(shotId)
                .subscribeOn(schedulersProvider.io());
    }

    public Single<List<Comment>> getShotComments(
            ShotCommentsRequestParams shotCommentsRequestParams
    ) {
        return commentsRepository.getComments(shotCommentsRequestParams)
                .subscribeOn(schedulersProvider.io());
    }

    public Completable saveImage(String imageUrl) {
        return imagesRepository.saveImage(imageUrl)
                .subscribeOn(schedulersProvider.io());
    }

}
