package room106.app.schedule.data.repositories

import room106.app.schedule.data.db.TasksDatabase
import room106.app.schedule.data.db.entities.Task

class TasksRepository(
    private val db: TasksDatabase
) {
    suspend fun insert(task: Task) = db.getTasksDao().insert(task)

    suspend fun delete(task: Task) = db.getTasksDao().delete(task)

    suspend fun deleteAllExcept(dates: List<String>) = db.getTasksDao().deleteAllExcept(dates)

    fun getTasksByDay(date: String) = db.getTasksDao().getTasksByDay(date)

    fun getAllTasks() = db.getTasksDao().getAllTasks()
}