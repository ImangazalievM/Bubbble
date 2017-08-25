package com.imangazalievm.bubbble.domain.usecases;

import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.repository.IImagesRepository;
import com.imangazalievm.bubbble.domain.usecases.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SaveShotImageUseCase extends CompletableUseCase<String> {

    private IImagesRepository imagesRepository;

    @Inject
    public SaveShotImageUseCase(@JobScheduler Scheduler jobScheduler,
                                @UiScheduler Scheduler uiScheduler,
                                IImagesRepository imagesRepository) {
        super(jobScheduler, uiScheduler);

        this.imagesRepository = imagesRepository;
    }

    @Override
    protected Completable buildCompletable(String shotImageUrl) {
        return imagesRepository.saveImage(shotImageUrl);
    }


}
