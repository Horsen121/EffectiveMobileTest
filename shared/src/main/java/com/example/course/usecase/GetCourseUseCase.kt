package com.example.course.usecase

import com.example.course.models.Course
import com.example.course.repositories.CourseRepository

class GetCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(id: Int): Course? {
        return try {
            repository.getCourseById(id,false)
        } catch (_: Exception) {
            null
        }
    }
}