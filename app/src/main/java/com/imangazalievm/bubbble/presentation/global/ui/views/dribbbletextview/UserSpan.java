package com.imangazalievm.bubbble.presentation.global.ui.views.dribbbletextview;

import android.content.res.ColorStateList;
import android.view.View;

import okhttp3.HttpUrl;

public abstract class UserSpan extends TouchableUrlSpan {

    public UserSpan(String url, ColorStateList textColor) {
        super(url, textColor);
    }

    @Override
    public void onClick(View widget) {
        HttpUrl url = HttpUrl.parse(getURL());
        String userId = url.pathSegments().get(0);
        onClick(Long.parseLong(userId));
    }

    public abstract void onClick(long useId);


}