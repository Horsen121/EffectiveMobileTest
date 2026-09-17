package com.example.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.CourseState
import com.example.course.models.Course
import com.example.course.usecase.GetFavouritesCoursesUseCase
import com.example.course.usecase.ToggleBookmarkUseCase
import com.example.ui.UiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    getFavouritesCoursesUseCase: GetFavouritesCoursesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val stringProvider: UiModule.StringResourceProvider
): ViewModel() {

    val state: StateFlow<CourseState> = getFavouritesCoursesUseCase()
        .map<List<Course>, CourseState> { courses ->
            CourseState.Content(courses)
        }
        .catch { error ->
            emit(CourseState.Error(error.localizedMessage ?: stringProvider.getString(R.string.favourites_error_db)))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CourseState.Loading
        )

    fun onRemoveBookmark(course: Course) {
        viewModelScope.launch {
            toggleBookmarkUseCase(course.id, false)
        }
    }
}