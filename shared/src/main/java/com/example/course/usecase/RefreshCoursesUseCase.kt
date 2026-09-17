package com.example.course.usecase

import com.example.course.repositories.CourseRepository
import javax.inject.Inject

class RefreshCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.refreshCourses()
}