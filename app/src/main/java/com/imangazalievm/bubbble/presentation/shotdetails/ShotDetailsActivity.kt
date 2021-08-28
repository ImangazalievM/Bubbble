package com.imangazalievm.bubbble.presentation.shotdetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.greenfrvr.hashtagview.HashtagView;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.shotdetails.DaggerShotDetailsPresenterComponent;
import com.imangazalievm.bubbble.di.shotdetails.ShotDetailsPresenterComponent;
import com.imangazalievm.bubbble.di.shotdetails.ShotDetailsPresenterModule;
import com.imangazalievm.bubbble.domain.global.models.Comment;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.presentation.global.ui.adapters.ShotCommentsAdapter;
import com.imangazalievm.bubbble.presentation.global.ui.base.MvpAppCompatActivity;
import com.imangazalievm.bubbble.presentation.global.ui.commons.AndroidPermissionsManager;
import com.imangazalievm.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener;
import com.imangazalievm.bubbble.presentation.global.ui.commons.glide.GlideCircleTransform;
import com.imangazalievm.bubbble.presentation.global.ui.views.dribbbletextview.DribbbleTextView;
import com.imangazalievm.bubbble.presentation.shotzoom.ShotZoomActivity;
import com.imangazalievm.bubbble.presentation.userprofile.UserProfileActivity;
import com.imangazalievm.bubbble.presentation.global.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ShotDetailsActivity extends MvpAppCompatActivity implements ShotDetailsView {

    private static final String KEY_SHOT_ID = "shot_id";

    public static Intent buildIntent(Context context, long shotId) {
        Intent intent = new Intent(context, ShotDetailsActivity.class);
        intent.putExtra(KEY_SHOT_ID, shotId);
        return intent;
    }

    private CoordinatorLayout shotDetailContainer;
    private Toolbar toolbar;

    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView commentsList;
    private ShotCommentsAdapter commentsAdapter;
    private ImageView shotImage;
    private ProgressBar shotImageProgressBar;
    private View shotDescription;
    private TextView title;
    private DribbbleTextView description;
    private TextView shotCreateDate;
    private View userProfileLayout;
    private TextView userName;
    private ImageView userAvatar;
    private TextView likesCount;
    private TextView viewsCount;
    private TextView bucketsCount;
    private TextView shareShotButton;
    private HashtagView hashtagView;
    private LinearLayoutManager commentsListLayoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);

    @InjectPresenter
    ShotDetailsPresenter shotDetailsPresenter;

    @ProvidePresenter
    ShotDetailsPresenter providePresenter() {
        long shotId = getIntent().getLongExtra(KEY_SHOT_ID, -1);
        ShotDetailsPresenterModule presenterModule = new ShotDetailsPresenterModule(shotId);

        ShotDetailsPresenterComponent presenterComponent = DaggerShotDetailsPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.getComponent())
                .shotDetailsPresenterModule(presenterModule)
                .build();

        return presenterComponent.getPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);

        initToolbar();
        initViews();

        shotDetailsPresenter.setPermissionsManager(new AndroidPermissionsManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        shotDetailsPresenter.removePermissionsManager();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        shotDetailContainer = (CoordinatorLayout) findViewById(R.id.shot_detail_container);

        loadingLayout = findViewById(R.id.loading_layout);
        noNetworkLayout = findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> shotDetailsPresenter.retryLoading());

        commentsList = (RecyclerView) findViewById(R.id.shot_comments);
        commentsListLayoutManager = new LinearLayoutManager(this);
        commentsList.setLayoutManager(commentsListLayoutManager);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(commentsListLayoutManager) {
            @Override
            public void onLoadMore() {
                shotDetailsPresenter.onLoadMoreCommentsRequest();
            }
        };

        shotImage = (ImageView) findViewById(R.id.shot_image);
        shotImageProgressBar = (ProgressBar) findViewById(R.id.shot_image_progress_bar);
        shotDescription = getLayoutInflater().inflate(R.layout.item_shot_description, commentsList, false);
        title = shotDescription.findViewById(R.id.shot_title);
        description = shotDescription.findViewById(R.id.shot_description);
        userProfileLayout = shotDescription.findViewById(R.id.user_profile_layout);
        userName = shotDescription.findViewById(R.id.user_name);
        userAvatar = shotDescription.findViewById(R.id.user_avatar);
        shotCreateDate = shotDescription.findViewById(R.id.shot_create_date);

        shotImage.setOnClickListener(v -> shotDetailsPresenter.onImageClicked());
        userProfileLayout.setOnClickListener(v -> shotDetailsPresenter.onShotAuthorProfileClicked());
        description.setOnLinkClickListener(url -> shotDetailsPresenter.onLinkClicked(url));
        description.setOnUserSelectedListener(userId -> shotDetailsPresenter.onCommentAuthorClick(userId));

        likesCount = shotDescription.findViewById(R.id.shot_likes_count);
        viewsCount = shotDescription.findViewById(R.id.shot_views_count);
        bucketsCount = shotDescription.findViewById(R.id.shot_buckets_count);
        shareShotButton = shotDescription.findViewById(R.id.share_shot);
        hashtagView = shotDescription.findViewById(R.id.shot_tags);
        hashtagView.addOnTagClickListener((item) -> {
            String tag = (String) item;
            shotDetailsPresenter.onTagClicked(tag);
            Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
        });

        likesCount.setOnClickListener(v -> shotDetailsPresenter.onLikeShotClicked());
        shareShotButton.setOnClickListener(v -> shotDetailsPresenter.onShareShotClicked());
    }

    @Override
    public void showShot(Shot shot) {
        shotDetailContainer.setVisibility(View.VISIBLE);

        //shot info
        title.setText(shot.getTitle());
        shotCreateDate.setText(dateFormat.format(shot.getCreatedAt()));
        if (shot.getDescription() != null) {
            description.setHtmlText(shot.getDescription());
        } else {
            description.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load(shot.getImages().best())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        shotDetailsPresenter.onImageLoadError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        shotDetailsPresenter.onImageLoadSuccess();
                        return false;
                    }
                })
                .into(shotImage);

        likesCount.setText(getResources().getQuantityString(R.plurals.likes, shot.getLikesCount(), shot.getLikesCount()));
        viewsCount.setText(getResources().getQuantityString(R.plurals.views, shot.getViewsCount(), shot.getViewsCount()));
        bucketsCount.setText(getResources().getQuantityString(R.plurals.buckets, shot.getBucketsCount(), shot.getBucketsCount()));
        hashtagView.setData(shot.getTags());

        //user info
        userName.setText(shot.getUser().getName());
        Glide.with(this)
                .load(shot.getUser().getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideCircleTransform(this))
                .into(userAvatar);

        //comments
        commentsAdapter = new ShotCommentsAdapter(this, shotDescription);
        commentsAdapter.setOnLinkClickListener(url -> shotDetailsPresenter.onLinkClicked(url));
        commentsAdapter.setOnUserSelectedListener(userId -> shotDetailsPresenter.onCommentAuthorClick(userId));
        commentsList.setAdapter(commentsAdapter);
        if (shot.getCommentsCount() > 0) {
            commentsList.addOnScrollListener(endlessRecyclerOnScrollListener);
        }
        showToolbarMenu();
    }

    private void showToolbarMenu() {
        toolbar.inflateMenu(R.menu.shot_details);
        toolbar.setOnMenuItemClickListener(this::onToolbarItemSelected);
    }

    private boolean onToolbarItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download_shot_image:
                shotDetailsPresenter.onDownloadImageClicked();
                return true;
            case R.id.open_in_browser:
                shotDetailsPresenter.onOpenShotInBrowserClicked();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void showLoadingProgress() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNewComments(List<Comment> newComments) {
        commentsAdapter.addItems(newComments);
    }

    @Override
    public void showCommentsLoadingProgress() {
        commentsAdapter.showLoadingIndicator();
    }

    @Override
    public void hideCommentsLoadingProgress() {
        commentsAdapter.hideLoadingIndicator();
    }

    @Override
    public void showNoComments() {
        commentsAdapter.setNoComments();
    }

    @Override
    public void showImageSavedMessage() {
        Snackbar.make(shotDetailContainer, R.string.image_saved_to_downloads_folder, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showStorageAccessRationaleMessage() {
        new AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
                .setTitle(R.string.storage_access_title)
                .setMessage(R.string.storage_access_rationale_message)
                .setPositiveButton(R.string.storage_access_ok_button, (dialog, which) -> shotDetailsPresenter.onDownloadImageClicked())
                .show();
    }

    @Override
    public void showAllowStorageAccessMessage() {
        new AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
                .setTitle(R.string.storage_access_title)
                .setMessage(R.string.storage_access_message)
                .setPositiveButton(R.string.storage_access_settings_button, (dialog, which) -> shotDetailsPresenter.onAppSettingsButtonClicked())
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
    public void showShotSharing(String shotTitle, String shotUrl) {
        AppUtils.sharePlainText(this, String.format("%s - %s", shotTitle, shotUrl));
    }

    @Override
    public void openInBrowser(String url) {
        AppUtils.openInChromeTab(this, url);
    }

    @Override
    public void openUserProfileScreen(long userId) {
        startActivity(UserProfileActivity.buildIntent(this, userId));
    }

    @Override
    public void openShotImageScreen(Shot shot) {
        startActivity(ShotZoomActivity.buildIntent(this, shot));
    }

    @Override
    public void showNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideImageLoadingProgress() {
        shotImageProgressBar.setVisibility(View.GONE);
    }

}