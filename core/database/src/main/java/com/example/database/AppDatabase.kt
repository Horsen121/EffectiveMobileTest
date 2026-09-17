package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.CourseDao
import com.example.database.entity.CourseEntity

@Database(
    entities = [
        CourseEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun courseDao(): CourseDao

    companion object {
        const val DATABASE_NAME = "effective_mobile_database"
    }
}