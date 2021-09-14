package room106.app.schedule.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import room106.app.schedule.data.db.entities.Task

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks WHERE day NOT IN (:dates)")
    suspend fun deleteAllExcept(dates: List<String>)

    @Query("SELECT * FROM tasks WHERE day = :date")
    fun getTasksByDay(date: String): LiveData<List<Task>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>
}