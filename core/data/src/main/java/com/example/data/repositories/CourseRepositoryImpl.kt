package com.example.data.repositories

import com.example.course.repositories.CourseRepository
import com.example.data.mappers.toDomain
import com.example.database.dao.CourseDao
import com.example.network.RemoteApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.course.models.Course
import com.example.data.mappers.toEntity
import com.example.data.mock.MockData

class CourseRepositoryImpl @Inject constructor(
    private val dao: CourseDao,
    private val api: RemoteApiService
): CourseRepository {

    override fun getAllCourses(): Flow<List<Course>> =
        dao.getAllCourses().map { it.toDomain() }

    override fun getFavouriteCourses(): Flow<List<Course>> =
        dao.getFavouriteCourses().map { it.toDomain() }

    override suspend fun getCourseById(id: Int): Course? = dao.getCourseById(id)?.toDomain()

    override suspend fun refreshCourses(): Result<Unit> = runCatching {
        val remoteCourses = MockData.getCourses() // api.getCourses()

        val entities = remoteCourses.map { dto ->
            val existingCourse = dao.getCourseById(dto.id)
            val currentLike = existingCourse?.hasLike ?: dto.hasLike
            dto.toEntity(hasLike = currentLike)
        }

        dao.insertCourses(entities)
    }

    override suspend fun toggleLike(courseId: Int, isLiked: Boolean) {
        dao.updateLikeStatus(courseId, isLiked)
    }
}