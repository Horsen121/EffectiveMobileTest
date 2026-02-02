package com.example.course.usecase

import com.example.course.models.Course
import com.example.course.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetFavouritesCoursesUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): Flow<List<Course>> {
        return flowOf(repository.getCourses(false)
            .first().filter { it.hasLike })
    }
}