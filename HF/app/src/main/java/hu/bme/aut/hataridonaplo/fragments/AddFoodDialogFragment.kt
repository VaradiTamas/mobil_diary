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
import hu.bme.aut.hataridonaplo.data.food.Food
import kotlinx.android.synthetic.main.fragment_add_food_dialog.view.*


class AddFoodDialogFragment  : DialogFragment() {
    interface NewFoodDialogListener {
        fun onFoodCreated(food: Food)
    }

    private lateinit var cal: EditText
    private lateinit var prot: EditText
    private lateinit var carb: EditText
    private lateinit var listener: AddFoodDialogFragment.NewFoodDialogListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragment_add_food_dialog, container, false)

        rootView.btnAddFood.setOnClickListener() {
            if (isValid()) {
                listener.onFoodCreated(getFood())
                dismiss()
            }
            else
                Toast.makeText(requireContext(), "Kerlek megfelelo adatokat adj meg", Toast.LENGTH_SHORT).show()
        }

        cal = rootView.findViewById(R.id.etCalories)
        prot = rootView.findViewById(R.id.etProtein)
        carb = rootView.findViewById(R.id.etCarbohydrate)

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
        listener = context as? AddFoodDialogFragment.NewFoodDialogListener
            ?: throw RuntimeException("Activity must implement the NewTaskDialogListener interface!")
    }

    private fun isValid(): Boolean {
        if (cal.text.isNotEmpty() && prot.text.isNotEmpty() && carb.text.isNotEmpty()){
            if(cal.text.length>6)
                return false
            if(prot.text.length>6)
                return false
            if(carb.text.length>6)
                return false
            return true
        }
        else
            return false
    }

    private fun getFood() = Food(
        id = null,
        calories = Integer(Integer.parseInt(cal.text.toString())),
        protein = Integer(Integer.parseInt(prot.text.toString())),
        carbohydrate = Integer(Integer.parseInt(carb.text.toString())),
        date = HomeFragment.pageDate
    )
}
