package com.example.domain.repositories

import com.example.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourses(fromNetwork: Boolean = false): Flow<List<Course>>
    suspend fun getCourseById(id: Int, fromNetwork: Boolean = false): Course?
    suspend fun insertCourse(course: Course)
    suspend fun deleteCourse(course: Course)
}