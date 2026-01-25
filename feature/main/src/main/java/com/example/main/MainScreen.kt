package com.example.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.Course
import com.example.ui.theme.EffectiveMobileTestTheme

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    viewModel: MainScreenVM = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    MainScreenContent(
        state,
        { viewModel.errorShown() },
        { viewModel.changeBookmarkOfCourse(it) },
        paddingValues
    )
}

@Composable
private fun MainScreenContent(
    state: CourseUiState,
    errorShown: () -> Unit,
    onBookmark: (Course) -> Unit,
    paddingValues: PaddingValues
) {
    if (state.isLoading && state.courses.isEmpty()) {
//        LoadingSpinner()
    } else {
        state.errorMessage?.let { msg ->
            LaunchedEffect(msg) {
//                    snackbarHostState.showSnackbar(msg)
                errorShown()
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    val courses = listOf(
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

    EffectiveMobileTestTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            MainScreenContent(CourseUiState(courses), {}, {}, padding)
        }
    }

}