package com.example.course.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.course.models.Course

@Composable
fun ColumnOfCourses(
    courses: List<Course>,
    onBookmark: (Course) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
         items(courses, key = {it}) { course ->
            CourseCard(
                course,
                onBookmark
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
