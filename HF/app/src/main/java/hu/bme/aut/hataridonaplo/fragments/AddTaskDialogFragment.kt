package hu.bme.aut.hataridonaplo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.hataridonaplo.R
import hu.bme.aut.hataridonaplo.data.exercise.Exercise
import kotlinx.android.synthetic.main.fragment_add_task_dialog.view.*

class AddTaskDialogFragment  : DialogFragment() {
    interface NewTaskDialogListener {
        fun onToDoCreated(newTask: Exercise)
    }

    private lateinit var toDoTask: EditText
    private lateinit var listener: NewTaskDialogListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView: View = inflater.inflate(R.layout.fragment_add_task_dialog, container, false)

        rootView.btnAddTask.setOnClickListener(){
            if (isValid()) {
                listener.onToDoCreated(getToDo())
                dismiss()
            }
        }

        toDoTask = rootView.findViewById(R.id.etTask)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = 800
        params.height = 800
        window.attributes = params
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewTaskDialogListener
            ?: throw RuntimeException("Activity must implement the NewTaskDialogListener interface!")
    }

    private fun isValid() = toDoTask.text.isNotEmpty()

    private fun getToDo() = Exercise(
        id = null,
        task = toDoTask.text.toString(),
        isDone = Integer(0),
        date = HomeFragment.pageDate
    )
}