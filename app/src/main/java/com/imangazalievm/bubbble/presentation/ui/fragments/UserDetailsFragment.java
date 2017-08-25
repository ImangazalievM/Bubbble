package com.imangazalievm.bubbble.presentation.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.DaggerUserDetailsPresenterComponent;
import com.imangazalievm.bubbble.di.UserDetailsPresenterComponent;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserDetailsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.UserInfoView;

public class UserDetailsFragment extends MvpAppCompatFragment implements UserInfoView {

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
        UserDetailsPresenterComponent userDetailsPresenterComponent = DaggerUserDetailsPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.component())
                .build();
        long userId = getArguments().getLong(USER_ID_ARG);
        return new UserDetailsPresenter(userDetailsPresenterComponent, userId);
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

        userShotsCount = (TextView) view.findViewById(R.id.user_shots_count);
        userLikesCount = (TextView) view.findViewById(R.id.user_likes_count);
        userBucketsCount = (TextView) view.findViewById(R.id.user_buckets_count);
        userFollowersCount = (TextView) view.findViewById(R.id.user_followers_count);
        userFollowingCount = (TextView) view.findViewById(R.id.user_followings_count);
        userProjectsCount = (TextView) view.findViewById(R.id.user_projects_count);
    }

    @Override
    public void showUser(User user) {
        userShotsCount.setText(getResources().getQuantityString(R.plurals.shots, user.getShotsCount(), user.getShotsCount()));
        userLikesCount.setText(getResources().getQuantityString(R.plurals.likes, user.getLikesCount(), user.getLikesCount()));
        userBucketsCount.setText(getResources().getQuantityString(R.plurals.buckets, user.getBucketsCount(), user.getBucketsCount()));
        userFollowersCount.setText(getResources().getQuantityString(R.plurals.followers, user.getFollowersCount(), user.getFollowersCount()));
        userFollowingCount.setText(getResources().getQuantityString(R.plurals.following, user.getFollowingsCount(), user.getFollowingsCount()));
        userProjectsCount.setText(getResources().getQuantityString(R.plurals.projects, user.getProjectsCount(), user.getProjectsCount()));
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
    public void showUserProfileSharing(User user) {

    }

}
