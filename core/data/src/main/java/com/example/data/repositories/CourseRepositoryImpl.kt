package com.example.data.repositories

import com.example.data.mappers.toDB
import com.example.data.mappers.toDomain
import com.example.database.dao.CourseDAO
import com.example.domain.models.Course as DomainCourse
import com.example.domain.repositories.CourseRepository
import com.example.network.RemoteApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepositoryImpl(
    private val dao: CourseDAO
): CourseRepository {
    override suspend fun getCourses(fromNetwork: Boolean): Flow<List<DomainCourse>> {
        return if(fromNetwork) {
            RemoteApi.retrofitService.getCourses().body().toDomain()
        } else dao.getCourses().map { it.toDomain() }
    }
    override suspend fun getCourseById(id: Int, fromNetwork: Boolean): DomainCourse? {
        return if(fromNetwork) {
            null
        } else dao.getCourseById(id)?.toDomain()
    }
    override suspend fun insertCourse(course: DomainCourse) = dao.insertCourse(course.toDB())
    override suspend fun deleteCourse(course: DomainCourse) = dao.deleteCourse(course.toDB())
}