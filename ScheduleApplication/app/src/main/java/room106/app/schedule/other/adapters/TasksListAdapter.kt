package room106.app.schedule.other.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import room106.app.schedule.R
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.databinding.TaskBinding
import room106.app.schedule.other.listeners.OnItemClickListener
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel

class TasksListAdapter(
    var tasks: List<Task>,
    private val viewModel: TasksViewModel,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = TaskBinding.bind(itemView)

        fun addOnItemClickListener(clickListener: OnItemClickListener, task: Task) {
            itemView.setOnClickListener {
                clickListener.onTaskClickListener(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        with(holder.binding) {
            tvTitle.text = task.title
            checkbox.isChecked = task.status

            checkbox.setOnCheckedChangeListener { _, b ->
                task.status = b
                viewModel.insert(task)
            }
        }
        holder.addOnItemClickListener(itemClickListener, task)
    }

    override fun getItemCount() = tasks.size
}