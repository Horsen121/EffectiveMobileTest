package com.example.ui

import android.content.Context
import androidx.annotation.StringRes
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Singleton
    class StringResourceProvider @Inject constructor(
        @ApplicationContext private val context: Context
    ) {
        fun getString(@StringRes resId: Int) = context.getString(resId)
    }
}