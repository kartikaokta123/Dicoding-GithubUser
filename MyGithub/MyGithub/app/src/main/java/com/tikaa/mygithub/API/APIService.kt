package com.tikaa.mygithub.API

import com.tikaa.mygithub.BuildConfig
import com.tikaa.mygithub.data.User
import com.tikaa.mygithub.data.SearchResponse
import com.tikaa.mygithub.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    companion object {
        private const val API_TOKEN = BuildConfig.GITHUB_TOKEN
    }

    @GET("users")
    @Headers("Authorization: token $API_TOKEN")
    fun getListUser(): Call<ArrayList<User>>

    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN")
    fun getSearch(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN")
    fun getDetail(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}