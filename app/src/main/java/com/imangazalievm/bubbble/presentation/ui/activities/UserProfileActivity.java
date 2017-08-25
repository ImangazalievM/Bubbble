package com.imangazalievm.bubbble.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.DaggerUserProfilePresenterComponent;
import com.imangazalievm.bubbble.di.UserProfilePresenterComponent;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserProfilePresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.UserProfileView;
import com.imangazalievm.bubbble.presentation.ui.adapters.UserProfilePagerAdapter;
import com.imangazalievm.bubbble.presentation.ui.commons.glide.GlideBlurTransformation;
import com.imangazalievm.bubbble.presentation.ui.commons.glide.GlideCircleTransform;
import com.imangazalievm.bubbble.presentation.ui.fragments.UserDetailsFragment;
import com.imangazalievm.bubbble.presentation.ui.fragments.UserFollowersFragment;
import com.imangazalievm.bubbble.presentation.ui.fragments.UserShotsFragment;
import com.imangazalievm.bubbble.presentation.ui.views.dribbbletextview.DribbbleTextView;
import com.imangazalievm.bubbble.presentation.utils.AppUtils;

public class UserProfileActivity extends BaseMvpActivity implements UserProfileView {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = -0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private static final String USER_ID = "user_id";

    public static Intent buildIntent(Context context, long userId) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(USER_ID, userId);
        return intent;
    }

    private View loadingLayout;
    private View noNetworkLayout;

    private CoordinatorLayout userProfileContainer;
    private AppBarLayout appBarLayout;
    private RelativeLayout userAvatarContainer;
    private TextView toolbarTitle;
    private ImageView headerBackground;
    private ImageView userAvatar;
    private TextView userName;
    private DribbbleTextView userBio;
    private TextView followButton;
    private ViewPager userProfileViewPager;
    private TabLayout userProfilePagerTabs;

    private boolean isTheTitleVisible = false;

    @InjectPresenter
    UserProfilePresenter shotDetailPresenter;

    @ProvidePresenter
    UserProfilePresenter providePresenter() {
        UserProfilePresenterComponent userProfilePresenterComponent = DaggerUserProfilePresenterComponent.builder()
                .applicationComponent(BubbbleApplication.component())
                .build();
        long userId = getIntent().getLongExtra(USER_ID, 0L);
        return new UserProfilePresenter(userProfilePresenterComponent, userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initViews() {
        loadingLayout = findViewById(R.id.loading_layout);
        noNetworkLayout = findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> shotDetailPresenter.retryLoading());

        userProfileContainer = (CoordinatorLayout) findViewById(R.id.user_profile_container);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            handleToolbarTitleVisibility(percentage);
        });

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        userAvatarContainer = (RelativeLayout) findViewById(R.id.header_image_container);
        headerBackground = (ImageView) findViewById(R.id.header_background);
        userAvatar = (ImageView) findViewById(R.id.user_avatar);
        userName = (TextView) findViewById(R.id.user_name);
        userBio = (DribbbleTextView) findViewById(R.id.user_bio);
        followButton = (TextView) findViewById(R.id.user_follow_button);

        userBio.setOnLinkClickListener(url -> shotDetailPresenter.onLinkClicked(url));
        userBio.setOnUserSelectedListener(useId -> shotDetailPresenter.onUserSelected(useId));
        followButton.setOnClickListener(v -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());

        userProfileViewPager = (ViewPager) findViewById(R.id.user_profile_pager);
        userProfilePagerTabs = (TabLayout) findViewById(R.id.user_profile_pager_tabs);
        userProfilePagerTabs.setupWithViewPager(userProfileViewPager);

        initParallaxValues();
    }

    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams headerBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) headerBackground.getLayoutParams();
        headerBackgroundLp.setParallaxMultiplier(PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR);
        headerBackground.setLayoutParams(headerBackgroundLp);

        CollapsingToolbarLayout.LayoutParams userAvatarContainerContainerLp =
                (CollapsingToolbarLayout.LayoutParams) userAvatarContainer.getLayoutParams();
        userAvatarContainerContainerLp.setParallaxMultiplier(PERCENTAGE_TO_HIDE_TITLE_DETAILS);
        userAvatarContainer.setLayoutParams(userAvatarContainerContainerLp);


    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!isTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                isTheTitleVisible = true;
            }
        } else {
            if (isTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                isTheTitleVisible = false;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }


    @Override
    public void showUser(User user) {
        userProfileContainer.setVisibility(View.VISIBLE);
        toolbarTitle.setText(user.getName());
        userName.setText(user.getName());
        if (user.getBio() != null || !user.getBio().isEmpty()) {
            userBio.setVisibility(View.VISIBLE);
            userBio.setHtmlText(user.getBio());
        }

        Glide.with(this)
                .load(user.getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideCircleTransform(this))
                .into(userAvatar);

        Glide.with(this)
                .load(user.getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new GlideBlurTransformation(this, 25))
                .into(headerBackground);

        setupProfilePager(user);
    }

    private void setupProfilePager(User user) {
        UserProfilePagerAdapter userProfilePagerAdapter = new UserProfilePagerAdapter(getSupportFragmentManager());
        userProfilePagerAdapter.addFragment(UserDetailsFragment.newInstance(this, user.getId()), getResources().getString(R.string.user_information));
        userProfilePagerAdapter.addFragment(UserShotsFragment.newInstance(user.getId()), getResources().getQuantityString(R.plurals.shots, user.getShotsCount(), user.getShotsCount()));
        userProfilePagerAdapter.addFragment(UserFollowersFragment.newInstance(user.getId()), getResources().getQuantityString(R.plurals.followers, user.getFollowersCount(), user.getFollowersCount()));
        userProfileViewPager.setAdapter(userProfilePagerAdapter);
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
    public void showNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.GONE);
    }

    @Override
    public void openUserProfileScreen(long userId) {
        startActivity(UserProfileActivity.buildIntent(this, userId));
    }

    @Override
    public void openInBrowser(String url) {
        AppUtils.openInChromeTab(this, url);
    }

    @Override
    public void showUserProfileSharing(User user) {
        AppUtils.sharePlainText(this, String.format("%s - %s", user.getName(), user.getHtmlUrl()));
    }

    @Override
    public void closeScreen() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                shotDetailPresenter.onBackPressed();
                return true;

            case R.id.share_profile:
                shotDetailPresenter.onShareProfileClicked();
                return true;

            case R.id.open_in_browser:
                shotDetailPresenter.onOpenInBrowserClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}