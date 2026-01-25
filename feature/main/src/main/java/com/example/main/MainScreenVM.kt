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
//        loadCurses(true) // for dev
        viewModelScope.launch { // for test
            val courses = mutableListOf(
                Course(
                    id = 100,
                    title = "Java-разработчик с нуля",
                    text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, " +
                            "работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                    price = 999,
                    rate = 4.9f,
                    startDate = "2024-05-22",
                    hasLike = false,
                    publishDate = "2024-02-02"
                ),
                Course(
                    id = 101,
                    title = "3D-дженералист",
                    text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, " +
                            "который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
                    price = 12000,
                    rate = 3.9f,
                    startDate = "2024-09-10",
                    hasLike = false,
                    publishDate = "2024-01-20"
                ),
                Course(
                    id = 102,
                    title = "Python Advanced. Для продвинутых",
                    text = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные " +
                            "приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, " +
                            "как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
                    price = 1299,
                    rate = 4.3f,
                    startDate = "2024-10-12",
                    hasLike = true,
                    publishDate = "2024-08-10"
                ),
                Course(
                    id = 103,
                    title = "Системный аналитик",
                    text = "Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.",
                    price = 1199,
                    rate = 4.5f,
                    startDate = "2024-04-15",
                    hasLike = false,
                    publishDate = "2024-01-13"
                ),
                Course(
                    id = 104,
                    title = "Аналитик данных",
                    text = "В этом уроке вы узнаете, кто такой аналитик данных и какие задачи он решает. " +
                            "А главное — мы расскажем, чему вы научитесь по завершении программы обучения профессии «Аналитик данных».",
                    price = 899,
                    rate = 4.7f,
                    startDate = "2024-06-20",
                    hasLike = false,
                    publishDate = "2024-03-12"
                )
            )
            val data = courseRepository.getCourses(false).first()
            if(data.isEmpty()) {
                _uiState.update { // for preview
                    it.copy(courses = courses)
                }.let {
                    _uiState.value.courses
                        .filter { it.hasLike }
                        .forEach {
                            courseRepository.insertCourse(it.copy(hasLike = true))
                        }
                }
            } else {
                courses.apply {
                    this.forEach {
                          this[this.indexOf(it)] = it.copy(hasLike = false)
                    }
                }
                val newData = data.map {
                    Pair(courses.indexOfFirst { el -> el.id == it.id }, it)
                }
                val updData = courses.apply {
                    newData.forEach {
                        this[it.first] = it.second
                    }
                }
                _uiState.update {
                    it.copy(
                        courses = updData
                    )
                }
            }
        }

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
                        errorMessage = e.localizedMessage
                            ?: stringProvider.getString(R.string.main_error_network)
                    )
                }

//                _uiState.update { it.copy(isLoading = true) }
//                try {
//                    val data = courseRepository.getCourses(false).first()
//
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            courses = data,
//                            errorMessage = null
//                        )
//                    }
//                } catch (e: Exception) {
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            errorMessage = e.localizedMessage
//                                ?: stringProvider.getString(R.string.main_error_network)
//                        )
//                    }
//                }
            }
        }
    }

    fun changeBookmarkOfCourse(course: Course) {
        viewModelScope.launch {
            if (course.hasLike)
                courseRepository.deleteCourse(course)
            else courseRepository.insertCourse(course.copy(hasLike = true))
        }.let {
            val idx = _uiState.value.courses.indexOf(course)
            _uiState.update {
                it.copy(
                    courses = it.courses.toMutableList().apply {
                        this[idx] = course.copy(hasLike = !course.hasLike)
                    }
                )
            }
        }
    }

    fun onSort() {
        _uiState.update {
            it.copy(
                courses = it.courses.sortedByDescending { course -> course.publishDate },
            )
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}