package room106.app.schedule.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task.view.*
import room106.app.schedule.R
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.ui.taskslist.TasksViewModel

class TasksListAdapter(
    var tasks: List<Task>,
    private val viewModel: TasksViewModel
): RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.itemView.tvTitle.text = task.title
        holder.itemView.checkbox.isChecked = task.status

        holder.itemView.checkbox.setOnCheckedChangeListener { compoundButton, b ->
            task.status = b
        }
    }

    override fun getItemCount(): Int = tasks.size
}