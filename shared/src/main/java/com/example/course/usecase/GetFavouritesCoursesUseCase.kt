package com.example.course.usecase

import com.example.course.models.Course
import com.example.course.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> = repository.getFavouriteCourses()
}