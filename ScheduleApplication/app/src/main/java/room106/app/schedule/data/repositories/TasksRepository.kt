package room106.app.schedule.data.repositories

import room106.app.schedule.data.db.TasksDatabase
import room106.app.schedule.data.db.entities.Task

class TasksRepository(
    private val db: TasksDatabase
) {
    suspend fun insert(task: Task) = db.getTasksDao().insert(task)

    suspend fun delete(task: Task) = db.getTasksDao().delete(task)

    fun getTasksByDay(day: String) = db.getTasksDao().getTasksByDay(day)
}