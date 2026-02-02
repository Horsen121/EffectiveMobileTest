package com.example.data.repositories

import com.example.course.repositories.CourseRepository
import com.example.data.mappers.toDB
import com.example.data.mappers.toDomain
import com.example.data.mock.MockData
import com.example.database.dao.CourseDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.course.models.Course as DomainCourse

class CourseRepositoryImpl @Inject constructor(
    private val dao: CourseDAO
): CourseRepository {
    override suspend fun getCourses(fromNetwork: Boolean): Flow<List<DomainCourse>> {
        return if(fromNetwork) {
            MockData.getCourses()
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