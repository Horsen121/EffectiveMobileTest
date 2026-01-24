package com.example.database.repositories

import com.example.database.dao.CourseDAO
import com.example.database.entity.Course

class CourseRepository(
    private val dao: CourseDAO
) {
    fun getPatients() = dao.getCourses()
    suspend fun getCourse(id: Int) = dao.getCourseById(id)
    suspend fun insertPatient(item: Course) = dao.insertCourse(item)
    suspend fun deletePatient(item: Course) = dao.deleteCourse(item)
}