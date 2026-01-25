package com.example.ui.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Int.toPrice(): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '\u00A0'
    }
    val formatter = DecimalFormat("#,###", symbols)
    return formatter.format(this)
}