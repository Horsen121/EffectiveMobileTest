package com.example.course.usecase

import com.example.course.models.Course
import com.example.course.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetCoursesUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): Flow<List<Course>> {
        try {
            val coursesFromRemote = repository.getCourses(true).first().apply {
                this.forEach { course ->
                    if(course.hasLike)
                        repository.insertCourse(course)
                }
            }
            val coursesFromLocal = repository.getCourses(false).first()

            val resCourses = coursesFromRemote.toMutableList()
            coursesFromLocal.forEach { course ->
                resCourses.replaceAll { if(it.id == course.id) course else it }
            }

            return flowOf(resCourses)
        } catch (_: Exception) {
            return flowOf(emptyList())
        }
    }
}