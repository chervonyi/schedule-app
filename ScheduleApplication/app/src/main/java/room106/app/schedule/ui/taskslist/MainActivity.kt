package room106.app.schedule.ui.taskslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import room106.app.schedule.R
import room106.app.schedule.ui.taskslist.fragments.TasksFragment

class MainActivity : AppCompatActivity() {

    private lateinit var tasksFragment: TasksFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasksFragment = TasksFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain, tasksFragment)
            commit()
        }
    }
}