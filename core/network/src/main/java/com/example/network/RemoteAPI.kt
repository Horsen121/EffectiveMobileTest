package com.example.network

import com.example.network.dto.CourseDto
import retrofit2.http.GET

interface RemoteApiService {
    @GET("/pictures")
    suspend fun getCourses(): List<CourseDto>
}