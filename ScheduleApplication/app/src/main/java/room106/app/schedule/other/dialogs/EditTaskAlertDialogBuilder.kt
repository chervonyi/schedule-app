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

class EditTaskAlertDialogBuilder(
    _context: Context,
    private val layoutInflater: LayoutInflater,
    private val viewModel: TasksViewModel,
    private val task: Task
): MaterialAlertDialogBuilder(_context) {

    init {
        val view = prepareView()
        val etTitle = view.findViewById<EditText>(R.id.etTitle).apply {
            setMultiLineCapSentencesAndDoneAction()
            setText(task.title)
        }

        setTitle("Edit task")
        setView(view)
        setPositiveButton("Save") { _, _, ->
            val newTitle = etTitle.text.toString()
            if (TaskValidation.validateTaskTitle(newTitle)) {
                task.title = newTitle
                viewModel.insert(task)
            } else {
                Toast.makeText(context, "Not valid title", Toast.LENGTH_LONG).show()
            }
        }
        .setNeutralButton("Delete") { _, _ ->
            viewModel.delete(task)
        }
        setNegativeButton("Dismiss") { _, _ -> }
    }

    private fun prepareView() = layoutInflater.inflate(R.layout.dialog_input, null)
}