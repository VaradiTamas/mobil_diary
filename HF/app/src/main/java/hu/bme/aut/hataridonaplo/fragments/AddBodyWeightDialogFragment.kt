package hu.bme.aut.hataridonaplo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.hataridonaplo.R
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeight
import kotlinx.android.synthetic.main.fragment_add_body_weight_dialog.view.*

class AddBodyWeightDialogFragment  : DialogFragment() {
    interface NewWeightDialogListener {
        fun onBodyWeightAdded(weight: BodyWeight)
    }

    private lateinit var kg: EditText
    private lateinit var listener: AddBodyWeightDialogFragment.NewWeightDialogListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragment_add_body_weight_dialog, container, false)

        rootView.btnAddBodyWeight.setOnClickListener() {
            if (isValid()) {
                listener.onBodyWeightAdded(getBodyWeight())
                dismiss()
            }
            else
                Toast.makeText(requireContext(), "Kerlek ne hagyd uresen es ne adj meg tul nagy sulyt", Toast.LENGTH_SHORT).show()
        }

        kg = rootView.findViewById(R.id.etBodyWeight)

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
        listener = context as? AddBodyWeightDialogFragment.NewWeightDialogListener
            ?: throw RuntimeException("Activity must implement the NewTaskDialogListener interface!")
    }

    private fun isValid() = !kg.text.isEmpty() && kg.text.length < 5

    private fun getBodyWeight() =
        BodyWeight(
            id = null,
            weight = kg.text.toString().toDouble(),
            date = HomeFragment.pageDate
        )
}