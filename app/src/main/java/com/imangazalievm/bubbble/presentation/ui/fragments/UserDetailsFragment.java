package com.imangazalievm.bubbble.presentation.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.DaggerUserDetailsPresenterComponent;
import com.imangazalievm.bubbble.di.UserDetailsPresenterComponent;
import com.imangazalievm.bubbble.di.modules.UserDetailsPresenterModule;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserDetailsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.UserDetailsView;
import com.imangazalievm.bubbble.presentation.utils.AppUtils;

import okhttp3.HttpUrl;

public class UserDetailsFragment extends MvpAppCompatFragment implements UserDetailsView {

    private static final String USER_ID_ARG = "user_id";

    public static UserDetailsFragment newInstance(Activity activity, long userId) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(USER_ID_ARG, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    UserDetailsPresenter shotsPresenter;

    @ProvidePresenter
    UserDetailsPresenter providePresenter() {
        long userId = getArguments().getLong(USER_ID_ARG);

        UserDetailsPresenterComponent presenterComponent = DaggerUserDetailsPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.component())
                .userDetailsPresenterModule(new UserDetailsPresenterModule(userId))
                .build();

        return presenterComponent.getPresenter();
    }

    private View loadingLayout;
    private View noNetworkLayout;

    private View userShotsButton;
    private View userLikesButton;
    private View userBucketsButton;
    private View userFollowersButton;
    private View userFollowingButton;
    private View userProjectsButton;

    private TextView userShotsCount;
    private TextView userLikesCount;
    private TextView userBucketsCount;
    private TextView userFollowersCount;
    private TextView userFollowingCount;
    private TextView userProjectsCount;

    private TextView userLocation;
    private TextView userTwitter;
    private TextView userWebsite;
    private View userTwitterButton;
    private View userWebsiteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {
        loadingLayout = view.findViewById(R.id.loading_layout);
        noNetworkLayout = view.findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> shotsPresenter.retryLoading());

        userShotsButton = view.findViewById(R.id.user_shots_button);
        userLikesButton = view.findViewById(R.id.user_likes_button);
        userBucketsButton = view.findViewById(R.id.user_buckets_button);
        userFollowersButton = view.findViewById(R.id.user_followers_button);
        userFollowingButton = view.findViewById(R.id.user_followings_button);
        userProjectsButton = view.findViewById(R.id.user_projects_button);

        userShotsCount = view.findViewById(R.id.user_shots_count);
        userLikesCount = view.findViewById(R.id.user_likes_count);
        userBucketsCount = view.findViewById(R.id.user_buckets_count);
        userFollowersCount = view.findViewById(R.id.user_followers_count);
        userFollowingCount = view.findViewById(R.id.user_followings_count);
        userProjectsCount = view.findViewById(R.id.user_projects_count);

        userLocation = view.findViewById(R.id.user_location);
        userTwitter = view.findViewById(R.id.user_twitter);
        userWebsite = view.findViewById(R.id.user_website);
        userTwitterButton = view.findViewById(R.id.user_twitter_button);
        userWebsiteButton = view.findViewById(R.id.user_website_button);
        userTwitterButton.setOnClickListener(v -> shotsPresenter.onUserTwitterButtonClicked());
        userWebsiteButton.setOnClickListener(v -> shotsPresenter.onUserWebsiteButtonClicked());
    }

    @Override
    public void showUser(User user) {
        userShotsCount.setText(getResources().getQuantityString(R.plurals.shots, user.getShotsCount(), user.getShotsCount()));
        userLikesCount.setText(getResources().getQuantityString(R.plurals.likes, user.getLikesCount(), user.getLikesCount()));
        userBucketsCount.setText(getResources().getQuantityString(R.plurals.buckets, user.getBucketsCount(), user.getBucketsCount()));
        userFollowersCount.setText(getResources().getQuantityString(R.plurals.followers, user.getFollowersCount(), user.getFollowersCount()));
        userFollowingCount.setText(getResources().getQuantityString(R.plurals.following, user.getFollowingsCount(), user.getFollowingsCount()));
        userProjectsCount.setText(getResources().getQuantityString(R.plurals.projects, user.getProjectsCount(), user.getProjectsCount()));
        userLocation.setText(user.getLocation());

        String userTwitterUrl = user.getLinks().getTwitter();
        if (userTwitterUrl != null) {
            userTwitterButton.setVisibility(View.VISIBLE);
            userTwitter.setText(getTwitterUserName(userTwitterUrl));
        }

        String userWebsiteUrl = user.getLinks().getWeb();
        if (userWebsiteUrl != null) {
            userWebsiteButton.setVisibility(View.VISIBLE);
            userWebsite.setText(userWebsiteUrl);
        }
    }

    private String getTwitterUserName(String twitterUrl) {
        return HttpUrl.parse(twitterUrl).pathSegments().get(0);
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
    public void openInBrowser(String url) {
        AppUtils.openInChromeTab(getActivity(), url);
    }

}
