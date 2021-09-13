package room106.app.schedule.other.listeners

import room106.app.schedule.data.db.entities.Task

interface OnItemClickListener {
    fun onTaskClickListener(task: Task)
}