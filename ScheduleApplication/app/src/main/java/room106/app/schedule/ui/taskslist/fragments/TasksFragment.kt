package room106.app.schedule.ui.taskslist.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModelFactory
import java.util.*

class TasksFragment : Fragment(R.layout.fragment_tasks), KodeinAware, OnItemClickListener,
    OnDayClickListener {

    override val kodein by closestKodein()
    private val factory: TasksViewModelFactory by instance()
    private lateinit var viewModel: TasksViewModel
    private lateinit var tasksAdapter: TasksListAdapter

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

    private lateinit var day: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(TasksViewModel::class.java)

        setUpDaysRecyclerView()
        setUpTasksRecyclerView()

        binding.fab.setOnClickListener(onFabClickListener)

        // Subscribe to observer
        observeTasksForDate(DateConversion.today())
    }

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
    }

    private fun setUpTasksRecyclerView() {
        tasksAdapter = TasksListAdapter(listOf(), viewModel, this)
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = tasksAdapter
        binding.rvTasks.setDivider(requireContext(), R.drawable.list_divider, DividerItemDecoration.VERTICAL)
    }
    //endregion

    @SuppressLint("NotifyDataSetChanged")
    fun observeTasksForDate(day: String) {
        this.day = day
        viewModel.getTasksByDay(day).observe(viewLifecycleOwner, Observer {
            tasksAdapter.tasks = it
            tasksAdapter.notifyDataSetChanged()
        })
    }

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

    override fun onSelectDayListener(date: Date) = observeTasksForDate(DateConversion.toString(date))
}

fun RecyclerView.setDivider(
    context: Context,
    res: Int,
    decoration: Int = DividerItemDecoration.VERTICAL
) {
    val divider = ContextCompat.getDrawable(context, res)!!
    val itemDecoration = DividerItemDecoration(context, decoration)
    itemDecoration.setDrawable(divider)
    addItemDecoration(itemDecoration)
}

fun EditText.setMultiLineCapSentencesAndDoneAction() {
    imeOptions = EditorInfo.IME_ACTION_DONE
    setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
}