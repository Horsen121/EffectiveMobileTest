package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDAO {
    @Query("SELECT * FROM `courses` WHERE hasLike = 1")
    fun getCourses(): Flow<List<Course>>

    @Query("SELECT * FROM `courses` WHERE id = :id")
    suspend fun getCourseById(id: Int): Course?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(item: Course)

    @Delete
    suspend fun deleteCourse(item: Course)
}