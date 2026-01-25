package com.example.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.Course
import com.example.ui.R
import com.example.ui.components.BodyText
import com.example.ui.components.LabelText
import com.example.ui.theme.EffectiveMobileTestTheme

@Composable
fun CourseCard(
    course: Course,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(236.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = (-32).dp)
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
                        modifier = Modifier
                            .padding(12.dp)
                    ) {

                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth(0.9f)
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LabelText(
                            text = R.string.ui_course_detailed,
                            isHeadline = false,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Image(
                            Icons.AutoMirrored.Filled.ArrowRightAlt,
                            stringResource(R.string.ui_course_detailed),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                            modifier = Modifier.scale(0.7f)
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
                CourseCard(course)
            }
        }
    }

}