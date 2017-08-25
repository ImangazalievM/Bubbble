package com.imangazalievm.bubbble.domain.usecases.base;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Отвечает за выполнение команд в нужном потоке, а также подписки и отписки
 */
public abstract class ObservableUseCase<RequestType, ResultType> {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    public ObservableUseCase(Scheduler jobScheduler, Scheduler uiScheduler) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    protected abstract Observable<ResultType> buildObservable(RequestType argument);

    public Observable<ResultType> getObservable() {
        return getObservable(null);
    }

    public Observable<ResultType> getObservable(RequestType argument) {
        return buildObservable(argument)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);
    }

}