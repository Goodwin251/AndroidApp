package com.example.pidrozdilua.wikiaip

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaApiService {
    @GET("w/api.php")
    suspend fun getPage(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("titles") titles: String
    ): WikipediaResponse

    @GET("w/api.php")
    fun getArticleContent(
        @Query("action") action: String = "parse",
        @Query("format") format: String = "json",
        @Query("page") page: String,
        @Query("prop") prop: String = "text|images",
        @Query("section") section: Int = 0
    ): Call<WikipediaArticleResponse>
}
