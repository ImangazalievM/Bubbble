package com.imangazalievm.bubbble.domain.usecases.base;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Отвечает за выполнение команд в нужном потоке, а также подписки и отписки
 */
public abstract class SingleUseCase<RequestType, ResultType> {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    public SingleUseCase(Scheduler jobScheduler, Scheduler uiScheduler) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    protected abstract Single<ResultType> buildSingle(RequestType argument);

    public Single<ResultType> getSingle() {
        return getSingle(null);
    }

    public Single<ResultType> getSingle(RequestType argument) {
        return buildSingle(argument)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);
    }

}