package com.example.favourites

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
class FavouritesVM @Inject constructor(
    private val courseRepository: CourseRepository,
    private val stringProvider: UiModule.StringResourceProvider
): ViewModel() {

    private val _uiState = MutableStateFlow(CourseUiState())
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()


    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val data = courseRepository.getCourses(false).first()

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
                        errorMessage = e.localizedMessage ?: stringProvider.getString(R.string.favourites_error_db)
                    )
                }
            }
        }
    }

    fun changeBookmarkOfCourse(course: Course) {
        viewModelScope.launch {
            courseRepository.deleteCourse(course)
        }.let {
            _uiState.update {
                it.copy(
                    courses = it.courses.toMutableList().apply {
                        this.remove(course)
                    }
                )
            }
        }
    }
}