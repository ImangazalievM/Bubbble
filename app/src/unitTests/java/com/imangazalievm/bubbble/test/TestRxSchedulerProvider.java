package com.imangazalievm.bubbble.test;

import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestRxSchedulerProvider extends RxSchedulersProvider {

    @Override
    public Scheduler getIoScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.trampoline();
    }

}
