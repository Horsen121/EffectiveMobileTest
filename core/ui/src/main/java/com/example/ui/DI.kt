package com.example.ui

import com.example.ui.utils.AndroidStringResourceProvider
import com.example.ui.utils.StringResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UiModule {
    @Binds
    @Singleton
    abstract fun bindStringResourceProvider(
        impl: AndroidStringResourceProvider
    ): StringResourceProvider
}