package com.example.data.mappers

import com.example.course.models.Course
import com.example.database.entity.CourseEntity
import com.example.network.dto.CourseDto

fun CourseDto.toEntity(hasLike: Boolean = this.hasLike): CourseEntity = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)

fun List<CourseDto>.toEntities(): List<CourseEntity> = map { it.toEntity() }

fun CourseEntity.toDomain(): Course = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)

fun List<CourseEntity>.toDomain(): List<Course> = map { it.toDomain() }

fun Course.toEntity(): CourseEntity = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)