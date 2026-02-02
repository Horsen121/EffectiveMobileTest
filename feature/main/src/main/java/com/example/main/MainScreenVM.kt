package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.CourseState
import com.example.course.models.Course
import com.example.course.usecase.DeleteCourseUseCase
import com.example.course.usecase.GetCoursesUseCase
import com.example.course.usecase.InsertCourseUseCase
import com.example.ui.UiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val getCourses: GetCoursesUseCase,
    private val deleteCourse: DeleteCourseUseCase,
    private val insertCourse: InsertCourseUseCase,
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
                val data = getCourses().first()
                _state.value = CourseState.Content(data)
            } catch (e: Exception) {
                _state.value = CourseState.Error(e.localizedMessage ?: stringProvider.getString(R.string.main_error_network))
            }
        }
    }

    fun changeBookmarkOfCourse(course: Course) {
        if (_state.value !is CourseState.Content)
            return

        viewModelScope.launch {
            if (course.hasLike)
                deleteCourse(course)
            else insertCourse(course.copy(hasLike = true))
        }.let {
            val idx = (_state.value as CourseState.Content).courses.indexOf(course)
            _state.value = CourseState.Content(
                (_state.value as CourseState.Content).courses.toMutableList().apply {
                    this[idx] = course.copy(hasLike = !course.hasLike)
                }
            )
        }
    }

    fun onSort() {
        _state.value = CourseState.Content((_state.value as CourseState.Content)
            .courses.sortedByDescending { course -> course.publishDate }
        )
    }
}