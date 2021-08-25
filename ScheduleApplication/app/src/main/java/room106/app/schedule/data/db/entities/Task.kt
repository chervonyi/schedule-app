package room106.app.schedule.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    var title: String,
    var status: Boolean,
    var day: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
