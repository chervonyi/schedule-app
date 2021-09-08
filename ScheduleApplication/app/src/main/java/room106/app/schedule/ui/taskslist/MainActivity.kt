package room106.app.schedule.ui.taskslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import room106.app.schedule.DaysFragment
import room106.app.schedule.R
import room106.app.schedule.databinding.ActivityMainBinding
import room106.app.schedule.other.DateConversion
import room106.app.schedule.other.FragmentTypes
import room106.app.schedule.other.OnDayClickListener
import room106.app.schedule.other.OnItemClickListener
import room106.app.schedule.ui.taskslist.fragments.CreateTaskFragment
import room106.app.schedule.ui.taskslist.fragments.EditTaskFragment
import room106.app.schedule.ui.taskslist.fragments.TasksFragment
import java.util.*

class MainActivity : AppCompatActivity(), OnItemClickListener, OnDayClickListener {

    private lateinit var daysFragment: DaysFragment
    private lateinit var tasksFragment: TasksFragment
    private lateinit var createTaskFragment: CreateTaskFragment
    private lateinit var editTaskFragment: EditTaskFragment
    private lateinit var binding: ActivityMainBinding

    private var currentFragmentId = 0
//    private var currentDate = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        daysFragment = DaysFragment(this)
        tasksFragment = TasksFragment(this)
        createTaskFragment = CreateTaskFragment(DateConversion.today())
        editTaskFragment = EditTaskFragment()

        switchMainFragment(tasksFragment, false, FragmentTypes.TasksFragment)
        setFragment(R.id.flHeader, daysFragment, false)

        binding.fab.setOnClickListener {
            switchMainFragment(createTaskFragment, true, FragmentTypes.CreateTaskFragment)
            binding.fab.hide()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.fab.show()
        currentFragmentId = 0
    }

    private fun switchMainFragment(fragment: Fragment, stack: Boolean, fragmentCode: Int) {
        if (currentFragmentId != fragmentCode) {
            setFragment(R.id.flBody, fragment, stack)
            currentFragmentId = fragmentCode
        }
    }

    private fun setFragment(id: Int, fragment: Fragment, stack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment)
            if (stack) { addToBackStack(null) }
            commit()
        }
    }

    override fun onTaskClickListener(id: Int) {
        editTaskFragment.arguments = prepareArguments(id)
        switchMainFragment(editTaskFragment, true, FragmentTypes.EditTaskFragment)
    }

    private fun prepareArguments(id: Int) = Bundle().apply {
        putInt("id", id)
    }

    override fun onSelectDayListener(date: Date) {
        val day = DateConversion.toString(date)

        // Update tasks list according to selected day
        tasksFragment.observeTasksForDate(day)

        // Set selected day as a day for new tasks
        createTaskFragment.day = day
    }
}