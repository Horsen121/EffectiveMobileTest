package com.example.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Float,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
)