package room106.app.schedule.ui.taskslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import room106.app.schedule.R
import room106.app.schedule.databinding.ActivityMainBinding
import room106.app.schedule.other.FragmentTypes
import room106.app.schedule.other.OnItemClickListener
import room106.app.schedule.ui.taskslist.fragments.CreateTaskFragment
import room106.app.schedule.ui.taskslist.fragments.EditTaskFragment
import room106.app.schedule.ui.taskslist.fragments.TasksFragment

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var tasksFragment: TasksFragment
    private lateinit var createTaskFragment: CreateTaskFragment
    private lateinit var editTaskFragment: EditTaskFragment
    private lateinit var binding: ActivityMainBinding

    private var currentFragmentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tasksFragment = TasksFragment(this)
        createTaskFragment = CreateTaskFragment()
        editTaskFragment = EditTaskFragment()

        setFragment(tasksFragment, false, FragmentTypes.TasksFragment)

        binding.fab.setOnClickListener {
            setFragment(createTaskFragment, true, FragmentTypes.CreateTaskFragment)
            binding.fab.hide()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.fab.show()
    }

    private fun setFragment(fragment: Fragment, stack: Boolean, fragmentCode: Int) {
        if (currentFragmentId == fragmentCode) return
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            if (stack) { addToBackStack(null) }
            commit()
        }
        currentFragmentId = fragmentCode
    }

    override fun onTaskClickListener(id: Int) {
        if (currentFragmentId == FragmentTypes.TasksFragment) {
            val args = Bundle().also {
                it.putInt("id", id)
            }
            editTaskFragment.arguments = args
            setFragment(editTaskFragment, true, FragmentTypes.EditTaskFragment)
        }
    }
}