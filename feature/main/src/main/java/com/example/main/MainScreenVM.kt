package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Course
import com.example.domain.repositories.CourseRepository
import com.example.ui.UiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CourseUiState(
    val courses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


@HiltViewModel
class MainScreenVM @Inject constructor(
    private val courseRepository: CourseRepository,
    private val stringProvider: UiModule.StringResourceProvider
): ViewModel() {
    private val _uiState = MutableStateFlow(CourseUiState())
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    init {
        loadCurses(true)
    }

    private fun loadCurses(fromNetwork: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val data = courseRepository.getCourses(fromNetwork).first()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        courses = data,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: stringProvider.getString(R.string.main_error_network)
                    )
                }
            }
        }
    }

    fun changeBookmarkOfCourse(course: Course) {
        viewModelScope.launch {
            if (course.hasLike)
                courseRepository.deleteCourse(course)
            else courseRepository.insertCourse(course.copy(hasLike = true))
        }.let {
//            val courseIdx = _courses.value.indexOf(course)
//            _courses.value.get(courseIdx) = course.copy(hasLike = !course.hasLike)
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}