package com.imangazalievm.bubbble.presentation.global.utils;

import android.app.Activity;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ShareCompat;

import com.imangazalievm.bubbble.R;

public class AppUtils {

    public static void sharePlainText(Activity activity, String textToShare) {
        ShareCompat.IntentBuilder
                .from(activity)
                .setText(textToShare)
                .setType("text/plain")
                .setChooserTitle(activity.getString(R.string.share_using))
                .startChooser();
    }

    public static void openInChromeTab(Activity activity, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(activity.getResources().getColor(R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(url));
    }

}
