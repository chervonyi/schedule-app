package room106.app.schedule.ui.taskslist

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import room106.app.schedule.R
import room106.app.schedule.ui.taskslist.fragments.TasksFragment

class MainActivity : AppCompatActivity() {

    private lateinit var tasksFragment: TasksFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lock portrait orientation only for phones.
        if (resources.getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }

        tasksFragment = TasksFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain, tasksFragment)
            commit()
        }
    }
}