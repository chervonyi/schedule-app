package room106.app.schedule.ui.taskslist.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import room106.app.schedule.R
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.databinding.FragmentTasksBinding
import room106.app.schedule.other.adapters.DaysListAdapter
import room106.app.schedule.other.adapters.TasksListAdapter
import room106.app.schedule.other.dialogs.CreateTaskAlertDialogBuilder
import room106.app.schedule.other.dialogs.EditTaskAlertDialogBuilder
import room106.app.schedule.other.listeners.OnDayClickListener
import room106.app.schedule.other.listeners.OnItemClickListener
import room106.app.schedule.other.operators.DateConversion
import room106.app.schedule.other.setDivider
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModelFactory
import java.util.*

class TasksFragment : Fragment(R.layout.fragment_tasks), KodeinAware, OnItemClickListener,
    OnDayClickListener {

    override val kodein by closestKodein()
    private val factory: TasksViewModelFactory by instance()
    private lateinit var viewModel: TasksViewModel
    private lateinit var tasksAdapter: TasksListAdapter
    private var day = DateConversion.today()
    private var lastUpdatedList: List<Task> = listOf()

    //region Binding
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(TasksViewModel::class.java)

        setUpDaysRecyclerView()
        setUpTasksRecyclerView()
        binding.fab.setOnClickListener(onFabClickListener)

        // Subscribe to observer
        viewModel.getAllTasks().observe(viewLifecycleOwner, {
            lastUpdatedList = it
            updateTasksList(day)
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateTasksList(day: String) {
        tasksAdapter.tasks = lastUpdatedList.filter { task -> task.day == day }
        tasksAdapter.notifyDataSetChanged()
    }

    //region Listeners
    override fun onTaskClickListener(task: Task) {
        activity?.let {
            EditTaskAlertDialogBuilder(requireContext(), it.layoutInflater, viewModel, task).show()
        }
    }

    private val onFabClickListener = View.OnClickListener {
        activity?.let {
            CreateTaskAlertDialogBuilder(requireContext(), it.layoutInflater, viewModel, day).show()
        }
    }

    override fun onSelectDayListener(date: Date) {
        day = DateConversion.toString(date)
        updateTasksList(day)
    }
    //endregion

    //region RecyclerView
    private fun setUpDaysRecyclerView() {
        val dates = DateConversion.createListDate(0, 9)
        val daysAdapter = DaysListAdapter(dates, this)
        binding.rvDays.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvDays.adapter = daysAdapter
        binding.rvDays.setDivider(requireContext(), R.drawable.days_divider, DividerItemDecoration.HORIZONTAL)

        // Delete old tasks
        viewModel.deleteAllExcept(DateConversion.toString(dates))
    }

    private fun setUpTasksRecyclerView() {
        tasksAdapter = TasksListAdapter(listOf(), viewModel, this)
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = tasksAdapter
        binding.rvTasks.setDivider(requireContext(), R.drawable.list_divider, DividerItemDecoration.VERTICAL)
    }
    //endregion
}
