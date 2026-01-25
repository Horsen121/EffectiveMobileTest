package com.example.data.mappers

import com.example.domain.models.Course as DomainCourse
import com.example.database.entity.Course as DBCourse
import com.example.network.dto.GetCourseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun GetCourseResponse.toDomain(): DomainCourse {
    return DomainCourse(
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
fun List<GetCourseResponse>?.toDomain(): Flow<List<DomainCourse>> {
    return flowOf(this?.map { it.toDomain() } ?: emptyList())
}

fun DBCourse.toDomain(): DomainCourse {
    return DomainCourse(
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
fun List<DBCourse>.toDomain(): List<DomainCourse> {
    return this.map { it.toDomain() }
}

fun DomainCourse.toDB(): DBCourse {
    return DBCourse(
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