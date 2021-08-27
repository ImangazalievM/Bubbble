package com.imangazalievm.bubbble.data.global.network;

import com.imangazalievm.bubbble.domain.global.models.Comment;
import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.domain.global.models.Like;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DribbbleApiService {

    /* Authenticated user methods */

    @GET("user")
    Single<User> getAuthenticatedUser();

    @GET("user/shots")
    Single<List<Shot>> getUserShots(
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    @GET("v1/user/likes")
    Single<List<Like>> getUserLikes(
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    @GET("shots/{shotId}/like")
    Single<Like> liked(@Path("shotId") long shotId);

    @POST("shots/{shotId}/like")
    Single<Like> like(@Path("shotId") long shotId);

    @DELETE("shots/{shotId}/like")
    Completable unlike(@Path("shotId") long shotId);

    @GET("user/following/{userId}")
    Completable following(@Path("userId") long userId);

    @PUT("users/{userId}/follow")
    Completable follow(@Path("userId") long userId);

    @DELETE("users/{userId}/follow")
    Completable unfollow(@Path("userId") long userId);

    /* Shots */

    @GET("shots")
    Single<List<Shot>> getShots(
            @Query("sort") String sort,
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    @GET("shots/{shotId}")
    Single<Shot> getShot(@Path("shotId") long id);

    @GET("users/{userId}/shots")
    Single<List<Shot>> getUserShots(
            @Path("userId") long userId,
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    @GET("user/following/shots")
    Single<List<Shot>> getFollowing(
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    @GET("shots/{shotId}/likes")
    Single<List<Like>> getShotLikes(
            @Path("shotId") long shotId,
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    /* Comments */

    @GET("shots/{id}/comments")
    Single<List<Comment>> getShotComments(
            @Path("id") long id,
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

    /* Users */

    @GET("users/{userId}")
    Single<User> getUser(@Path("userId") long userId);

    @GET("users/{userId}/followers")
    Single<List<Follow>> getUserFollowers(
            @Path("userId") long userId,
            @Query("page") int page,
            @Query("per_page") int pageSize
    );

}
