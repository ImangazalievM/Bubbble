package com.imangazalievm.bubbble.test;

import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestSchedulersProvider extends SchedulersProvider {

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.trampoline();
    }

}
