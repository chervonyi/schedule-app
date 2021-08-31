package room106.app.schedule.ui.taskslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import room106.app.schedule.data.repositories.TasksRepository

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(
    private val repository: TasksRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(repository) as T
    }
}