package com.bubbble.data.global.network

import com.bubbble.domain.global.models.*
import retrofit2.http.*

interface DribbbleApi {

    /* Authenticated user methods */
    @GET("user")
    suspend fun authenticatedUser(): User

    @GET("user/shots")
    suspend fun getUserShots(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Shot>

    @GET("v1/user/likes")
    suspend fun getUserLikes(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Like>

    @GET("shots/{shotId}/like")
    suspend fun liked(@Path("shotId") shotId: Long): Like

    @POST("shots/{shotId}/like")
    suspend fun like(@Path("shotId") shotId: Long): Like

    @DELETE("shots/{shotId}/like")
    suspend fun unlike(@Path("shotId") shotId: Long)

    @GET("user/following/{userId}")
    suspend fun following(@Path("userId") userId: Long)

    @PUT("users/{userId}/follow")
    suspend fun follow(@Path("userId") userId: Long)

    @DELETE("users/{userId}/follow")
    suspend fun unfollow(@Path("userId") userId: Long)

    /* Shots */
    @GET("shots")
    suspend fun getShots(
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Shot>

    @GET("shots/{shotId}")
    suspend fun getShot(@Path("shotId") id: Long): Shot

    @GET("users/{userId}/shots")
    suspend fun getUserShots(
        @Path("userId") userId: Long,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Shot>

    @GET("user/following/shots")
    suspend fun getFollowing(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Shot>

    @GET("shots/{shotId}/likes")
    suspend fun getShotLikes(
        @Path("shotId") shotId: Long,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Like>

    /* Comments */
    @GET("shots/{id}/comments")
    suspend fun getShotComments(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Comment>

    /* Users */
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): User

    @GET("users/{userId}/followers")
    suspend fun getUserFollowers(
        @Path("userId") userId: Long,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Follow>

}