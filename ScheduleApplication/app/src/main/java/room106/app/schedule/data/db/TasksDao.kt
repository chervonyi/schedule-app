package room106.app.schedule.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import room106.app.schedule.data.db.entities.Task

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE day = :day")
    fun getTasksByDay(day: String): LiveData<List<Task>>
}