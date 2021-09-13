package room106.app.schedule.other.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import room106.app.schedule.R
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.other.operators.TaskValidation
import room106.app.schedule.ui.taskslist.fragments.setMultiLineCapSentencesAndDoneAction
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModel

class CreateTaskAlertDialogBuilder(
    _context: Context,
    private val layoutInflater: LayoutInflater,
    private val viewModel: TasksViewModel,
    private val day: String
): MaterialAlertDialogBuilder(_context) {

    init {
        val view = prepareView()
        val etTitle = view.findViewById<EditText>(R.id.etTitle).apply {
            setMultiLineCapSentencesAndDoneAction()
        }

        setTitle("Create new task")
        setView(view)
        setPositiveButton("Create") { _, _, ->
            val title = etTitle.text.toString()
            if (TaskValidation.validateTaskTitle(title)) {
                val task = Task(title, false, day)
                viewModel.insert(task)
            } else {
                Toast.makeText(context, "Not valid title", Toast.LENGTH_LONG).show()
            }
        }
        setNegativeButton("Cancel") { _, _ -> }
    }

    private fun prepareView() = layoutInflater.inflate(R.layout.dialog_input, null)
}