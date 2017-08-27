package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.repository.ICommentsRepository;
import com.imangazalievm.bubbble.domain.repository.IImagesRepository;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ShotDetailsInteractor {

    private IShotsRepository shotsRepository;
    private ICommentsRepository commentsRepository;
    private IImagesRepository imagesRepository;

    @Inject
    public ShotDetailsInteractor(IShotsRepository shotsRepository,
                                 ICommentsRepository commentsRepository,
                                 IImagesRepository imagesRepository) {
        this.shotsRepository = shotsRepository;
        this.commentsRepository = commentsRepository;
        this.imagesRepository = imagesRepository;
    }

    public Single<Shot> getShot(long shotId) {
        return shotsRepository.getShot(shotId);
    }

    public Single<List<Comment>> getShotComments(ShotCommentsRequestParams shotCommentsRequestParams) {
        return commentsRepository
                .getComments(shotCommentsRequestParams);
    }

    public Completable saveImage(String imageUrl) {
        return imagesRepository.saveImage(imageUrl);
    }

}
