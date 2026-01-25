package com.example.data

import android.content.Context
import androidx.room.Room
import com.example.data.repositories.CourseRepositoryImpl
import com.example.database.AppDB
import com.example.database.dao.CourseDAO
import com.example.domain.repositories.CourseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindCourseRepository(impl: CourseRepositoryImpl): CourseRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext context: Context): AppDB {
        return Room.databaseBuilder(
            context,
            AppDB::class.java,
            AppDB.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideVisitDao(db: AppDB): CourseDAO = db.courseDao()
}