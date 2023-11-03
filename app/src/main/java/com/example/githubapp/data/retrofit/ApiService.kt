package com.example.githubapp.data.retrofit

import com.example.githubapp.data.response.FollowerResponse
import com.example.githubapp.data.response.FollowingResponse
import com.example.githubapp.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(@Query("q") query: String ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<FollowerResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<FollowingResponse>>
}

data class UserResponse(
    val total_count: Int,
    val items: List<SearchResponse>
)
