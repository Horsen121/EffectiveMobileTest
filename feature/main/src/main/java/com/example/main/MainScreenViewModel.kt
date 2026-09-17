package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.models.Course
import com.example.course.usecase.GetCoursesUseCase
import com.example.course.usecase.RefreshCoursesUseCase
import com.example.course.usecase.ToggleBookmarkUseCase
import com.example.ui.utils.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getCoursesUseCase: GetCoursesUseCase,
    private val refreshCoursesUseCase: RefreshCoursesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val stringProvider: StringResourceProvider
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _isSortedByDate = MutableStateFlow(false)

    private val _effects = Channel<MainUiEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    val state: StateFlow<MainUiState> = combine(
        getCoursesUseCase(),
        _isLoading,
        _isSortedByDate
    ) { courses, isLoading, isSorted ->
        val displayedCourses = if (isSorted) {
            courses.sortedByDescending { it.publishDate }
        } else {
            courses
        }
        MainUiState(
            courses = displayedCourses,
            isLoading = isLoading,
            isSortedByDate = isSorted
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState(isLoading = true)
    )

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            refreshCoursesUseCase()
                .onFailure { error ->
                    _effects.send(MainUiEffect.ShowToast(error.localizedMessage ?: stringProvider.getString(R.string.main_error_network)))
                }
            _isLoading.value = false
        }
    }

    fun onBookmarkClicked(course: Course) {
        viewModelScope.launch {
            toggleBookmarkUseCase(course.id, !course.hasLike)
        }
    }

    fun onToggleSort() {
        _isSortedByDate.value = !_isSortedByDate.value
    }
}