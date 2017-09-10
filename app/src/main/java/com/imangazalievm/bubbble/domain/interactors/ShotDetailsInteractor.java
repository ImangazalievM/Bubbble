package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.repository.CommentsRepository;
import com.imangazalievm.bubbble.domain.repository.ImagesRepository;
import com.imangazalievm.bubbble.domain.repository.ShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ShotDetailsInteractor {

    private ShotsRepository shotsRepository;
    private CommentsRepository commentsRepository;
    private ImagesRepository imagesRepository;

    @Inject
    public ShotDetailsInteractor(ShotsRepository shotsRepository,
                                 CommentsRepository commentsRepository,
                                 ImagesRepository imagesRepository) {
        this.shotsRepository = shotsRepository;
        this.commentsRepository = commentsRepository;
        this.imagesRepository = imagesRepository;
    }

    public Single<Shot> getShot(long shotId) {
        return shotsRepository.getShot(shotId);
    }

    public Single<List<Comment>> getShotComments(ShotCommentsRequestParams shotCommentsRequestParams) {
        return commentsRepository.getComments(shotCommentsRequestParams);
    }

    public Completable saveImage(String imageUrl) {
        return imagesRepository.saveImage(imageUrl);
    }

}
