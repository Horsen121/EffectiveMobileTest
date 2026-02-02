package com.example.course

import com.example.course.repositories.CourseRepository
import com.example.course.usecase.DeleteCourseUseCase
import com.example.course.usecase.GetCourseUseCase
import com.example.course.usecase.GetCoursesUseCase
import com.example.course.usecase.GetFavouritesCoursesUseCase
import com.example.course.usecase.InsertCourseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedCourseModule {

    @Provides
    fun getCoursesUseCase(repository: CourseRepository): GetCoursesUseCase = GetCoursesUseCase(repository)

    @Provides
    fun getFavouritesCoursesUseCase(repository: CourseRepository): GetFavouritesCoursesUseCase = GetFavouritesCoursesUseCase(repository)

    @Provides
    fun getCourseUseCase(repository: CourseRepository): GetCourseUseCase = getCourseUseCase(repository)

    @Provides
    fun insertCourseUseCase(repository: CourseRepository): InsertCourseUseCase = InsertCourseUseCase(repository)

    @Provides
    fun deleteCourseUseCase(repository: CourseRepository): DeleteCourseUseCase = DeleteCourseUseCase(repository)
}