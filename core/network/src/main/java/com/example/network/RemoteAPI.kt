package com.example.network

import com.example.network.dto.GetCourseResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "url"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface RemoteApiService {
    @GET
    suspend fun getCourses(): Response<List<GetCourseResponse>>
}

object RemoteApi {
    val retrofitService : RemoteApiService by lazy {
        retrofit.create(RemoteApiService::class.java)
    }
}