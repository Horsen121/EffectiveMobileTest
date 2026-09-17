package com.example.favourites

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.course.CourseState
import com.example.course.models.Course
import com.example.course.ui.ColumnOfCourses
import com.example.ui.components.BodyText
import com.example.ui.components.TitleText
import com.example.ui.theme.EffectiveMobileTestTheme

@Composable
fun FavouritesScreen(
    paddingValues: PaddingValues,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Crossfade(targetState = state, label = "FavouritesCrossfade") { currentState ->
        when (currentState) {
            is CourseState.Initial, CourseState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CourseState.Content -> {
                FavouritesScreenContent(
                    courses = currentState.courses,
                    onBookmarkClick = viewModel::onRemoveBookmark,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is CourseState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    BodyText(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun FavouritesScreenContent(
    courses: List<Course>,
    onBookmarkClick: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TitleText(text = R.string.favourites_title)

        if (courses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                BodyText(
                    text = stringResource(R.string.favourites_empty),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            ColumnOfCourses(
                courses = courses,
                onBookmark = onBookmarkClick
            )
        }
    }
}

@Preview
@Composable
private fun FavouritesScreenPreview() {
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
    ).filter { it.hasLike }

    EffectiveMobileTestTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            FavouritesScreenContent(courses, {}, Modifier.padding(padding))
        }
    }
}