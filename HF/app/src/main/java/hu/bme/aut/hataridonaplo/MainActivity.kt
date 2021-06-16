package hu.bme.aut.hataridonaplo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeight
import hu.bme.aut.hataridonaplo.data.exercise.Exercise
import hu.bme.aut.hataridonaplo.data.food.Food
import hu.bme.aut.hataridonaplo.fragments.AddBodyWeightDialogFragment
import hu.bme.aut.hataridonaplo.fragments.AddFoodDialogFragment
import hu.bme.aut.hataridonaplo.fragments.AddTaskDialogFragment
import hu.bme.aut.hataridonaplo.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_nav_list.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), AddTaskDialogFragment.NewTaskDialogListener, AddFoodDialogFragment.NewFoodDialogListener,
    AddBodyWeightDialogFragment.NewWeightDialogListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("myTitle")

        bottom_nav_view.setupWithNavController(nav_host_fragment.findNavController())
    }

    override fun onToDoCreated(newTask: Exercise) {
        thread {
            val newId = HomeFragment.taskDatabase.exerciseDao().insert(newTask)
            val newtoDoTask = newTask.copy(
                id = newId
            )
            runOnUiThread {
                HomeFragment.adapterToDo.addToDo(newtoDoTask)
            }
        }
    }

    override fun onFoodCreated(food: Food) {
        HomeFragment.addFood(food)
    }

    override fun onBodyWeightAdded(weight: BodyWeight) {
        HomeFragment.addWeight(weight)
    }
}