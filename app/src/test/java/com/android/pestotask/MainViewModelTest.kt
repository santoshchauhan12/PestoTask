package com.android.pestotask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.pestotask.db.TaskNote
import com.android.pestotask.model.TaskStatus
import com.android.pestotask.repo.TaskRepository
import com.android.pestotask.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val taskRepository by lazy { mock<TaskRepository>() }

    @Mock
    private lateinit var observer: Observer<MutableList<TaskNote>?>

    private lateinit var mainViewModel: MainViewModel

    private var userId = "PestoUser123"

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        mainViewModel = MainViewModel(taskRepository)
        mainViewModel.setUserid(userId)
        mainViewModel.listNote.observeForever(observer)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        mainViewModel.listNote.removeObserver(observer)
    }

    @Test
    fun `insertNote should call repository and update LiveData`() {
        val testObserver = mainViewModel.listNote.testObserver()
        val taskNote = TaskNote(id =1,
            userid = userId,
            title = "coding",
            description = "write unit test",
            status = TaskStatus.TODO.name,
            createdTime = 123456)

        val taskNoteYoga = TaskNote(id =2,
            userid = userId,
            title = "Design",
            description = "MVVM",
            status = TaskStatus.TODO.name,
            createdTime = 1234569)

        runTest {
            mainViewModel.insertNote(taskNote)
            mainViewModel.insertNote(taskNoteYoga)
            verify(taskRepository).insertNote(taskNote)
            mainViewModel.getAllNotes()

            testObserver.awaitValue()
            val res = testObserver.observedValues()
            Assert.assertEquals(3, res.size)
        }


        verify(observer, times(3)).onChanged(any())

    }

    @Test
    fun `updateNote should update listNote`() {
        val taskNote = TaskNote(id =1,
            userid = userId,
            title = "coding",
            description = "write unit test",
            status = TaskStatus.TODO.name,
            createdTime = 123456)
        runTest {
            mainViewModel.updateNote(taskNote)
            verify(taskRepository).updateNote(taskNote)
            verify(taskRepository).getAllNotes(mainViewModel.getUserId().orEmpty())
        }

        verify(observer, times(1)).onChanged(any())
    }

    @Test
    fun `getAllNotes should update listNote`() {
        val testObserver = mainViewModel.listNote.testObserver()

        runTest {
           mainViewModel.getAllNotes()
        }

        runTest {
            verify(taskRepository).getAllNotes(mainViewModel.getUserId().orEmpty())

            testObserver.awaitValue()
            val value = testObserver.observedValues()
            Assert.assertEquals(1, value.size)
        }

    }

    private fun runTest(block: suspend () -> Unit) {
        testDispatcher.runBlockingTest {
            block()
        }
    }
}