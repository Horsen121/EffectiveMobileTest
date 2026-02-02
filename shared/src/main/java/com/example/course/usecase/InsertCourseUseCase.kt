package com.example.course.usecase

import com.example.course.models.Course
import com.example.course.repositories.CourseRepository

class InsertCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(course: Course) {
        repository.insertCourse(course)
    }
}