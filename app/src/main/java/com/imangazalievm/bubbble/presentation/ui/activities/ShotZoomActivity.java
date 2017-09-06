package com.imangazalievm.bubbble.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.DaggerShotZoomPresenterComponent;
import com.imangazalievm.bubbble.di.ShotZoomPresenterComponent;
import com.imangazalievm.bubbble.di.modules.ShotZoomPresenterModule;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.presentation.mvp.presenters.ShotZoomPresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotZoomView;
import com.imangazalievm.bubbble.presentation.ui.commons.AndroidPermissionsManager;
import com.imangazalievm.bubbble.presentation.utils.AppUtils;

public class ShotZoomActivity extends MvpAppCompatActivity implements ShotZoomView {

    private static final String KEY_SHOT_TITLE = "shot_title";
    private static final String KEY_SHOT_URL = "shot_url";
    private static final String KEY_IMAGE_URL = "image_url";

    public static Intent buildIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotZoomActivity.class);
        intent.putExtra(KEY_SHOT_TITLE, shot.getTitle());
        intent.putExtra(KEY_SHOT_URL, shot.getHtmlUrl());
        intent.putExtra(KEY_IMAGE_URL, shot.getImages().best());
        return intent;
    }

    private Toolbar toolbar;
    private ViewGroup shotZoomContainer;
    private View loadingLayout;
    private View errorLayout;

    private PhotoView shotImage;

    @InjectPresenter
    ShotZoomPresenter shotZoomPresenter;

    @ProvidePresenter
    ShotZoomPresenter providePresenter() {
        String shotTitle = getIntent().getStringExtra(KEY_SHOT_TITLE);
        String imageUrl = getIntent().getStringExtra(KEY_IMAGE_URL);
        String shotUrl = getIntent().getStringExtra(KEY_SHOT_URL);
        ShotZoomPresenterModule presenterModule = new ShotZoomPresenterModule(shotTitle, shotUrl, imageUrl);

        ShotZoomPresenterComponent presenterComponent = DaggerShotZoomPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.getComponent())
                .shotZoomPresenterModule(presenterModule)
                .build();

        return presenterComponent.getPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_zoom);

        initToolbar();
        initViews();

        shotZoomPresenter.setPermissionsManager(new AndroidPermissionsManager(this));
    }



    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        shotZoomContainer = (ViewGroup) findViewById(R.id.shot_zoom_container);
        loadingLayout = findViewById(R.id.loading_layout);
        errorLayout = findViewById(R.id.error_layout);
        errorLayout.findViewById(R.id.open_in_browser_button).setOnClickListener(v -> shotZoomPresenter.onOpenInBrowserClicked());

        shotImage = (PhotoView) findViewById(R.id.shot_image);
    }

    @Override
    public void showShot(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        shotZoomPresenter.onImageLoadError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        shotZoomPresenter.onImageLoadSuccess();
                        return false;
                    }
                })
                .into(shotImage);

        showToolbarMenu();
    }

    @Override
    public void showLoadingProgress() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        loadingLayout.setVisibility(View.GONE);
    }

    private void showToolbarMenu() {
        toolbar.inflateMenu(R.menu.shot_zoom_menu);
        toolbar.setOnMenuItemClickListener(this::onToolbarItenSelected);
    }

    private boolean onToolbarItenSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download_shot_image:
                shotZoomPresenter.onDownloadImageClicked();
                return true;
            case R.id.share_shot:
                shotZoomPresenter.onShareClicked();
                return true;
            case R.id.open_in_browser:
                shotZoomPresenter.onOpenInBrowserClicked();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void showShotSharing(String shotTitle, String shotUrl) {
        AppUtils.sharePlainText(this, String.format("%s - %s", shotTitle, shotUrl));
    }

    @Override
    public void openInBrowser(String url) {
        AppUtils.openInChromeTab(this, url);
    }

    @Override
    public void showErrorLayout() {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorLayout() {
        errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showImageSavedMessage() {
        Snackbar.make(shotZoomContainer, R.string.image_saved_to_downloads_folder, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showStorageAccessRationaleMessage() {
        new AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
                .setTitle(R.string.storage_access_title)
                .setMessage(R.string.storage_access_rationale_message)
                .setPositiveButton(R.string.storage_access_ok_button, (dialog, which) -> shotZoomPresenter.onDownloadImageClicked())
                .show();
    }

    @Override
    public void showAllowStorageAccessMessage() {
        new AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
                .setTitle(R.string.storage_access_title)
                .setMessage(R.string.storage_access_message)
                .setPositiveButton(R.string.storage_access_settings_button, (dialog, which) -> shotZoomPresenter.onAppSettingsButtonClicked())
                .show();
    }

    @Override
    public void openAppSettingsScreen() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}