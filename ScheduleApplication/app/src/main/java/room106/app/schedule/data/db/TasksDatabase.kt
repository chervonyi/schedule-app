package room106.app.schedule.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import room106.app.schedule.data.db.entities.Task

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TasksDatabase: RoomDatabase() {

    abstract fun getTasksDao(): TasksDao

    companion object {
        private var instance: TasksDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                TasksDatabase::class.java, "TasksDB.db").build()
    }
}