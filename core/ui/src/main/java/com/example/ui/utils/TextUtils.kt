package com.example.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toTextDate(): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    val date = LocalDate.parse(this)
    val formated = date.format(formatter).split(" ")
    return "${formated[0]} ${formated[1].replaceFirstChar { it.uppercase() }} ${formated[2]}"
}