package com.example.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.database.entity.Course

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    viewModel: MainScreenVM = hiltViewModel()
) {
    val courses = viewModel.courses.collectAsState()
    MainScreenContent(
        courses.value,
        { viewModel.changeBookmarkOfCourse(it) },
        paddingValues
    )
}

@Composable
private fun MainScreenContent(
    courses: List<Course>,
    onBookmark: (Course) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        
    ) {

    }
}