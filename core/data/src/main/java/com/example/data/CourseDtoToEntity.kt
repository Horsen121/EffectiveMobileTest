package com.example.data

import com.example.database.entity.Course
import com.example.network.dto.GetCourseResponse

fun GetCourseResponse.toEntity(): Course {
    return Course(
        id = this.id,
        title = this.title,
        text = this.text,
        price = this.price,
        rate = this.rate,
        startDate = this.startDate,
        hasLike = this.hasLike,
        publishDate = this.publishDate,
    )
}

fun List<GetCourseResponse>.toEntities(): List<Course> {
    return this.map { it.toEntity() }
}