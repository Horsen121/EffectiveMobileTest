package com.example.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.Course
import com.example.ui.R
import com.example.ui.components.BackgroundRow
import com.example.ui.components.BodyText
import com.example.ui.components.LabelText
import com.example.ui.theme.EffectiveMobileTestTheme
import com.example.ui.utils.toTextDate

@Composable
fun CourseCard(
    course: Course,
    onBookmark: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(236.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painterResource(R.drawable.mock_course_image),
                        stringResource(R.string.ui_course_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BackgroundRow(
                                isRound = true,
                                height = 28.dp
                            ) {
                                Image(
                                    painterResource(R.drawable.ui_bookmark),
                                    stringResource(R.string.ui_course_bookmark),
                                    modifier = Modifier
                                        .clickable(onClick = { onBookmark(course) })
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BackgroundRow(
                                isCard = true
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Image(
                                        painterResource(R.drawable.ui_star),
                                        null
                                    )
                                    LabelText(
                                        course.rate.toString(),
                                        isHeadline = false
                                    )
                                }
                            }
                            BackgroundRow(
                                isCard = true,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                LabelText(
                                    course.startDate.toTextDate(),
                                    isHeadline = false
                                )
                            }
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(11.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth(0.9f)
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1f)
            ) {
                LabelText(
                    text = course.title
                )

                BodyText(
                    text = course.text,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LabelText(
                        text = "${course.price} ₽"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LabelText(
                            text = R.string.ui_course_detailed,
                            isHeadline = false,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Image(
                            painterResource(R.drawable.ui_right_arrow),
                            stringResource(R.string.ui_course_detailed),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CourseCardPreview() {
    val course = Course(
        id = 100,
        title = "Java-разработчик с нуля",
        text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, " +
                "работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
        price = 999,
        rate = 4.9f,
        startDate = "2024-05-22",
        hasLike = false,
        publishDate = "2024-02-02"
    )

    EffectiveMobileTestTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                CourseCard(course, {})
            }
        }
    }

}