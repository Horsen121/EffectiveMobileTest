package com.example.main

import app.cash.turbine.test
import com.example.course.models.Course
import com.example.course.repositories.CourseRepository
import com.example.course.usecase.GetCoursesUseCase
import com.example.course.usecase.RefreshCoursesUseCase
import com.example.course.usecase.ToggleBookmarkUseCase
import com.example.test.MainDispatcherRule
import com.example.ui.utils.StringResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeCoursesFlow = MutableStateFlow<List<Course>>(emptyList())
    private var toggledLikeId: Int? = null

    private val fakeRepository = object : CourseRepository {
        override fun getAllCourses(): Flow<List<Course>> = fakeCoursesFlow
        override fun getFavouriteCourses(): Flow<List<Course>> = fakeCoursesFlow
        override suspend fun getCourseById(id: Int): Course? = null
        override suspend fun refreshCourses(): Result<Unit> = Result.success(Unit)
        override suspend fun toggleLike(courseId: Int, isLiked: Boolean) {
            toggledLikeId = courseId
        }
    }

    private val fakeStringProvider = object : StringResourceProvider {
        override fun getString(resId: Int) = "Mocked string"
    }

    private lateinit var viewModel: MainScreenViewModel

    private val testCourse = Course(
        id = 1,
        title = "Kotlin Basics",
        text = "Learn Kotlin",
        price = 1000,
        rate = 4.8f,
        startDate = "2026-01-01",
        hasLike = false,
        publishDate = "2026-01-01"
    )

    @Before
    fun setUp() {
        val getCoursesUseCase = GetCoursesUseCase(fakeRepository)
        val refreshCoursesUseCase = RefreshCoursesUseCase(fakeRepository)
        val toggleBookmarkUseCase = ToggleBookmarkUseCase(fakeRepository)

        viewModel = MainScreenViewModel(
            getCoursesUseCase = getCoursesUseCase,
            refreshCoursesUseCase = refreshCoursesUseCase,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
            stringProvider = fakeStringProvider
        )
    }

    @Test
    fun `state reflects courses emitted by repository flow`() = runTest {
        viewModel.state.test {
            assertEquals(emptyList<Course>(), awaitItem().courses)

            fakeCoursesFlow.value = listOf(testCourse)

            val updatedState = awaitItem()
            assertEquals(1, updatedState.courses.size)
            assertEquals("Kotlin Basics", updatedState.courses[0].title)
        }
    }

    @Test
    fun `onToggleSort changes isSortedByDate flag`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertFalse(initialState.isSortedByDate)

            viewModel.onToggleSort()
            val firstToggleState = awaitItem()
            assertTrue(firstToggleState.isSortedByDate)

            viewModel.onToggleSort()
            val secondToggleState = awaitItem()
            assertFalse(secondToggleState.isSortedByDate)
        }
    }

    @Test
    fun `onBookmarkClicked triggers repository toggleLike`() = runTest {
        viewModel.onBookmarkClicked(testCourse)

        assertEquals(1, toggledLikeId)
    }
}