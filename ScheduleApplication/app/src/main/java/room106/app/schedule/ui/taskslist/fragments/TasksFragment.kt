package room106.app.schedule.ui.taskslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import room106.app.schedule.databinding.FragmentTasksBinding
import room106.app.schedule.other.OnItemClickListener
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel
import room106.app.schedule.other.TasksListAdapter
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModelFactory

class TasksFragment(private val onItemClickListener: OnItemClickListener) :
    Fragment(R.layout.fragment_tasks), KodeinAware {

    override val kodein by closestKodein()
    private val factory: TasksViewModelFactory by instance()
    private lateinit var viewModel: TasksViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(TasksViewModel::class.java)

        // Setup RecyclerView
        val tasksAdapter = TasksListAdapter(listOf(), viewModel, onItemClickListener)
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = tasksAdapter
        binding.rvTasks.setDivider(R.drawable.list_divider)

        // Subscribe to observer
        viewModel.getTasksByDay().observe(viewLifecycleOwner, Observer {
            tasksAdapter.tasks = it
            tasksAdapter.notifyDataSetChanged()
        })
    }

    private fun RecyclerView.setDivider(res: Int) {
        val divider = ContextCompat.getDrawable(requireContext(), res)!!
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(divider)
        addItemDecoration(decoration)
    }



}