package room106.app.schedule.ui.taskslist.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.data.repositories.TasksRepository

class TasksViewModel(
    private val repository: TasksRepository
): ViewModel() {

    fun insert(task: Task) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(task)
    }

    fun delete(task: Task) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(task)
    }

    fun getTasksByDay() = repository.getTasksByDay()

    fun getTask(id: Int) = repository.getTask(id)
}