package com.imangazalievm.bubbble.domain.usecases.base;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;

/**
 * Отвечает за выполнение команд в нужном потоке, а также подписки и отписки
 */
public abstract class MaybeUseCase<RequestType, ResultType> {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    public MaybeUseCase(Scheduler jobScheduler, Scheduler uiScheduler) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    protected abstract Maybe<ResultType> buildMaybe(RequestType argument);

    public Maybe<ResultType> getMaybe() {
        return getMaybe(null);
    }

    public Maybe<ResultType> getMaybe(RequestType argument) {
        return buildMaybe(argument)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);
    }

}