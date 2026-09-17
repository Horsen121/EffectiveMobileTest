package com.example.course.usecase

import com.example.course.repositories.CourseRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int, isLiked: Boolean) {
        repository.toggleLike(courseId, isLiked)
    }
}