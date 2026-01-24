package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.toEntities
import com.example.database.entity.Course
import com.example.database.repositories.CourseRepository
import com.example.network.RemoteApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val courseRepository: CourseRepository,
): ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    init {
        viewModelScope.launch {
            RemoteApi.retrofitService.getCourses().let{
                if (it.isSuccessful) {
                    _courses.value = it.body()?.toEntities() ?: emptyList()
                }
            }
        }
    }

    infix fun changeBookmarkOfCourse(course: Course) {
        viewModelScope.launch {
            if (course.hasLike)
                courseRepository.deleteCourse(course)
            else courseRepository.insertCourse(course.copy(hasLike = true))
        }.let {
//            val courseIdx = _courses.value.indexOf(course)
//            _courses.value.get(courseIdx) = course.copy(hasLike = !course.hasLike)
        }
    }
}