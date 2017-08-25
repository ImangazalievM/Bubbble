package com.imangazalievm.bubbble.domain.usecases.base;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

/**
 * Отвечает за выполнение команд в нужном потоке, а также подписки и отписки
 */
public abstract class CompletableUseCase<RequestType> {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    public CompletableUseCase(Scheduler jobScheduler, Scheduler uiScheduler) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    protected abstract Completable buildCompletable(RequestType argument);

    public Completable getCompletable() {
        return getCompletable(null);
    }

    public Completable getCompletable(RequestType argument) {
        return buildCompletable(argument)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);
    }

}