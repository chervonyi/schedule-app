package room106.app.schedule.ui.taskslist.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import room106.app.schedule.R
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.databinding.FragmentCreateTaskBinding
import room106.app.schedule.databinding.FragmentEditTaskBinding
import room106.app.schedule.other.TaskValidation
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModelFactory

class CreateTaskFragment : Fragment(R.layout.fragment_create_task), KodeinAware {

    override val kodein by closestKodein()
    private val factory: TasksViewModelFactory by instance()
    private lateinit var viewModel: TasksViewModel

    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(TasksViewModel::class.java)

        binding.bSave.setOnClickListener {

            val title = binding.etTitle.text.toString()
            if (TaskValidation.validateTaskTitle(title)) {
                val task = Task(title, false, "10/10/1997")
                binding.etTitle.text?.clear()
                viewModel.insert(task)
                activity?.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Not valid title", Toast.LENGTH_LONG).show()
            }
        }

        binding.etTitle.setMultiLineCapSentencesAndDoneAction()
    }

    private fun EditText.setMultiLineCapSentencesAndDoneAction() {
        imeOptions = EditorInfo.IME_ACTION_DONE
        setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
    }
}