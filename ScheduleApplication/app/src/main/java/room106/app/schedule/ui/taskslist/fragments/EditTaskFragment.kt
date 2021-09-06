package room106.app.schedule.ui.taskslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import room106.app.schedule.R
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.databinding.FragmentEditTaskBinding
import room106.app.schedule.other.TaskValidation
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModelFactory

class EditTaskFragment : Fragment(R.layout.fragment_edit_task), KodeinAware {

    override val kodein by closestKodein()
    private val factory: TasksViewModelFactory by instance()
    private lateinit var viewModel: TasksViewModel

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(TasksViewModel::class.java)

        binding.etTitle.text?.clear()
        binding.bSave.setOnClickListener(onSaveButtonClick)
        binding.etTitle.setMultiLineCapSentencesAndDoneAction()

        arguments?.let {
            val id = it.getInt("id")
            viewModel.getTask(id).observe(viewLifecycleOwner, Observer { task ->
                this.task = task
                binding.etTitle.setText(task.title)
                // TODO - Update date TextView
            })
        }
    }

    private val onSaveButtonClick = View.OnClickListener {
        task?.let {
            val newTitle = binding.etTitle.text.toString()
            if (TaskValidation.validateTaskTitle(newTitle)) {
                it.title = newTitle
                viewModel.insert(it)
                activity?.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Not valid title", Toast.LENGTH_LONG).show()
            }
        }
    }
}