package com.example.domain

import com.example.domain.models.Course

sealed interface CourseState {
    data object Initial : CourseState
    data object Loading : CourseState
    data class Error(val message: String) : CourseState
    data class Content(val courses: List<Course>) : CourseState
}