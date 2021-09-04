package com.bubbble.coreui.ui.views.dribbbletextview;

import android.content.res.ColorStateList;
import android.view.View;

public abstract class LinkSpan extends TouchableUrlSpan {

    public LinkSpan(String url, ColorStateList textColor) {
        super(url, textColor);
    }

    @Override
    public void onClick(View widget) {
        onClick(getURL());
    }

    public abstract void onClick(String url);


}