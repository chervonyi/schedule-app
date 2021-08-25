package room106.app.schedule.ui.taskslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tasks.*
import room106.app.schedule.R
import room106.app.schedule.data.db.TasksDatabase
import room106.app.schedule.data.repositories.TasksRepository
import room106.app.schedule.other.TasksListAdapter

class TasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val database = TasksDatabase(this)
        val repository = TasksRepository(database)
        val factory = TasksViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, factory).get(TasksViewModel::class.java)

        val tasksAdapter = TasksListAdapter(listOf(), viewModel)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter

        viewModel.getTasksByDay("10/10/2021").observe(this, Observer {
            tasksAdapter.tasks = it
            tasksAdapter.notifyDataSetChanged()
        })
    }
}