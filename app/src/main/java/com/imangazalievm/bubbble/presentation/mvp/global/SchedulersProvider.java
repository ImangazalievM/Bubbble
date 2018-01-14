package com.imangazalievm.bubbble.presentation.mvp.global;

import javax.inject.Inject;

import io.reactivex.CompletableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersProvider {

    @Inject
    public SchedulersProvider() {
    }

    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }

    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler newThread() {
        return Schedulers.newThread();
    }

    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }

}
