package com.imangazalievm.bubbble.test;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulersProvider extends SchedulersProvider {

    private final TestScheduler testScheduler = new TestScheduler();

    @Override
    public Scheduler ui() {
        return testScheduler;
    }

    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    @Override
    public Scheduler io() {
        return testScheduler;
    }

    @Override
    public Scheduler newThread() {
        return testScheduler;
    }

    @Override
    public Scheduler trampoline() {
        return testScheduler;
    }

    public TestScheduler testScheduler() {
        return testScheduler;
    }

}
