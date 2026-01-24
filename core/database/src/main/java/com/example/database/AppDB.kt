package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.CourseDAO
import com.example.database.entity.Course

@Database(
    entities = [
        Course::class,
    ],
    version = 1
)
abstract class AppDB: RoomDatabase() {
    abstract fun courseDao(): CourseDAO

    companion object {
        const val DATABASE_NAME = "effective_mobile_database"
    }
}