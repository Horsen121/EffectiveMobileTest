package com.example.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.CourseState
import com.example.course.models.Course
import com.example.course.repositories.CourseRepository
import com.example.ui.UiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesVM @Inject constructor(
    private val courseRepository: CourseRepository,
    private val stringProvider: UiModule.StringResourceProvider
): ViewModel() {

    private val _state = MutableStateFlow<CourseState>(CourseState.Initial)
    val state: StateFlow<CourseState> = _state.asStateFlow()


    fun loadData() {
        if (_state.value is CourseState.Loading || _state.value is CourseState.Content)
            return

        _state.value = CourseState.Loading
        viewModelScope.launch {
            try {
                val data = courseRepository.getCourses(true).first()
                _state.value = CourseState.Content(data)
            } catch (e: Exception) {
                _state.value = CourseState.Error(e.localizedMessage ?: stringProvider.getString(R.string.favourites_error_db))
            }
        }
    }

    fun changeBookmarkOfCourse(course: Course) {
        if (_state.value !is CourseState.Content)
            return

        viewModelScope.launch {
            courseRepository.deleteCourse(course)
        }.let {
            _state.value = CourseState.Content(
                (_state.value as CourseState.Content).courses.toMutableList().apply {
                    this.remove(course)
                }
            )
        }
    }
}