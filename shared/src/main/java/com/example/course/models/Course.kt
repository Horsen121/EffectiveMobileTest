package com.example.course.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Float,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
) : Parcelable
