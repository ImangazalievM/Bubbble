package com.imangazalievm.bubbble.presentation.ui.activities;

import android.content.Context;

import com.arellomobile.mvp.MvpAppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseMvpActivity extends MvpAppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
