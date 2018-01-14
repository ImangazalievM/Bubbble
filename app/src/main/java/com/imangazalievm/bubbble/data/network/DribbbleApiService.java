package com.imangazalievm.bubbble.data.network;

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

    @GET("v1/user")
    Single<User> getAuthenticatedUser();

    @GET("v1/user/shots")
    Single<List<Shot>> getUserShots(@Query("page") int page,
                                    @Query("per_page") int pageSize);

    @GET("v1/user/likes")
    Single<List<Like>> getUserLikes(@Query("page") int page,
                                    @Query("per_page") int pageSize);

    @GET("v1/shots/{shotId}/like")
    Single<Like> liked(@Path("shotId") long shotId);

    @POST("v1/shots/{shotId}/like")
    Single<Like> like(@Path("shotId") long shotId);

    @DELETE("v1/shots/{shotId}/like")
    Completable unlike(@Path("shotId") long shotId);

    @GET("v1/user/following/{userId}")
    Completable following(@Path("userId") long userId);

    @PUT("v1/users/{userId}/follow")
    Completable follow(@Path("userId") long userId);

    @DELETE("v1/users/{userId}/follow")
    Completable unfollow(@Path("userId") long userId);

    /* Shots */

    @GET("v1/shots")
    Single<List<Shot>> getShots(@Query("sort") String sort,
                                @Query("page") int page,
                                @Query("per_page") int pageSize);

    @GET("v1/shots/{shotId}")
    Single<Shot> getShot(@Path("shotId") long id);

    @GET("v1/users/{userId}/shots")
    Single<List<Shot>> getUserShots(@Path("userId") long userId,
                                    @Query("page") int page,
                                    @Query("per_page") int pageSize);

    @GET("v1/user/following/shots")
    Single<List<Shot>> getFollowing(@Query("page") int page,
                                    @Query("per_page") int pageSize);

    @GET("v1/shots/{shotId}/likes")
    Single<List<Like>> getShotLikes(@Path("shotId") long shotId,
                                    @Query("page") int page,
                                    @Query("per_page") int pageSize);

    /* Comments */

    @GET("v1/shots/{id}/comments")
    Single<List<Comment>> getShotComments(@Path("id") long id,
                                          @Query("page") int page,
                                          @Query("per_page") int pageSize);

    /* Users */

    @GET("v1/users/{userId}")
    Single<User> getUser(@Path("userId") long userId);

    @GET("v1/users/{userId}/followers")
    Single<List<Follow>> getUserFollowers(@Path("userId") long userId,
                                          @Query("page") int page,
                                          @Query("per_page") int pageSize);

}
