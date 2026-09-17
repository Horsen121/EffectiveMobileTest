package com.example.main

import com.example.course.models.Course

data class MainUiState(
    val courses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val isSortedByDate: Boolean = false
)

sealed interface MainUiEffect {
    data class ShowToast(val message: String) : MainUiEffect
}