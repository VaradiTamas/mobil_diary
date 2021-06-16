package hu.bme.aut.hataridonaplo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.hataridonaplo.R
import kotlinx.android.synthetic.main.fragment_add_dialog.view.*


class AddDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView: View = inflater.inflate(R.layout.fragment_add_dialog, container, false)


        rootView.btnTask.setOnClickListener(){
            var dialog = AddTaskDialogFragment()
            dismiss()
            dialog.show(requireActivity().supportFragmentManager, "valami")
        }

        rootView.btnFood.setOnClickListener(){
            var dialog = AddFoodDialogFragment()
            dismiss()
            dialog.show(requireActivity().supportFragmentManager, "valami")
        }

        rootView.btnWeight.setOnClickListener(){
            var dialog = AddBodyWeightDialogFragment()
            dismiss()
            dialog.show(requireActivity().supportFragmentManager, "valami")
        }

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
}