package com.example.course.repositories

import com.example.course.models.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getAllCourses(): Flow<List<Course>>
    fun getFavouriteCourses(): Flow<List<Course>>
    suspend fun getCourseById(id: Int): Course?
    suspend fun refreshCourses(): Result<Unit>
    suspend fun toggleLike(courseId: Int, isLiked: Boolean)
}