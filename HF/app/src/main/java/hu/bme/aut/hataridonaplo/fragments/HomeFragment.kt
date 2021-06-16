package hu.bme.aut.hataridonaplo.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.hataridonaplo.R
import hu.bme.aut.hataridonaplo.adapter.DoneListAdapater
import hu.bme.aut.hataridonaplo.adapter.ToDoListAdapater
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeight
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeightDatabase
import hu.bme.aut.hataridonaplo.data.exercise.Exercise
import hu.bme.aut.hataridonaplo.data.exercise.ExerciseDatabase
import hu.bme.aut.hataridonaplo.data.food.Food
import hu.bme.aut.hataridonaplo.data.food.FoodDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.concurrent.thread


class HomeFragment : Fragment(R.layout.fragment_home), ToDoListAdapater.ToDoItemClickListener, DoneListAdapater.DoneItemClickListener{

    private lateinit var recyclerViewToDo: RecyclerView
    private lateinit var recyclerViewDone: RecyclerView

    companion object {
        public var pageDate: Date = Date()

        public lateinit var adapterToDo: ToDoListAdapater
        public lateinit var adapterDone: DoneListAdapater

        public lateinit var taskDatabase: ExerciseDatabase
        public lateinit var foodDatabase: FoodDatabase
        public lateinit var bodyWeightDatabase: BodyWeightDatabase

        private lateinit var cal: TextView
        private lateinit var prot: TextView
        private lateinit var carb: TextView
        private lateinit var kilo: TextView

        public fun addFood(food: Food){
            thread{foodDatabase.foodDao().insert(food)}
            var from: Date = Date()
            var to: Date = Date()
            from.year= pageDate.year
            from.month= pageDate.month
            from.date= pageDate.date
            from.hours=0
            to.year= pageDate.year
            to.month= pageDate.month
            to.date= pageDate.date
            to.hours=24
            thread{
            cal.text = foodDatabase.foodDao().getAllCaloriesForDate(from, to).toString()
            prot.text = foodDatabase.foodDao().getAllProteinForDate(from, to).toString()
            carb.text = foodDatabase.foodDao().getAllCarbohydrateForDate(from, to).toString()}
        }

        public fun addWeight(bodyWeight: BodyWeight){
            thread{
                bodyWeightDatabase.bodyWeightDao().insert(bodyWeight)
            }
            var from: Date = Date()
            var to: Date = Date()
            from.year= pageDate.year
            from.month= pageDate.month
            from.date= pageDate.date
            from.hours=0
            to.year= pageDate.year
            to.month= pageDate.month
            to.date= pageDate.date
            to.hours=24
            thread{
                kilo.text = "${bodyWeight.weight.toString()} kg"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cal = view.findViewById(R.id.tvCalories)
        prot = view.findViewById(R.id.tvProtein)
        carb = view.findViewById(R.id.tvCarbohydrate)
        kilo = view.findViewById(R.id.tvKilogram)

        taskDatabase = Room.databaseBuilder(
            requireContext(),
            ExerciseDatabase::class.java,
            "all_exercises"
        ).build()

        foodDatabase = Room.databaseBuilder(
            requireContext(),
            FoodDatabase::class.java,
            "foods"
        ).build()

        bodyWeightDatabase = Room.databaseBuilder(
            requireContext(),
            BodyWeightDatabase::class.java,
            "bodyweights"
        ).build()

        var oneDay: Date = Date(86400000)
        var tempDate: Date = Date()

        btnLeft.setOnClickListener(){
            var milliseconds = pageDate.time-oneDay.time
            tempDate.time = milliseconds
            changeDate(tempDate)
        }

        btnRight.setOnClickListener(){
            var milliseconds = pageDate.time+oneDay.time
            tempDate.time = milliseconds
            changeDate(tempDate)
        }

        initCurrentDate(pageDate)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recyclerViewToDo = rwToDo
        adapterToDo = ToDoListAdapater(this)
        recyclerViewToDo.adapter = adapterToDo

        recyclerViewDone = rwDone
        adapterDone = DoneListAdapater(this)
        recyclerViewDone.adapter = adapterDone

        loadItemsInBackground()
    }

    public fun changeDate(date: Date){
        pageDate = date
        initCurrentDate(date)
        loadItemsInBackground()
    }

    private fun initCurrentDate(c: Date) {
        val year = c.year +1900
        val month = c.month +1
        val day = c.date
        val nameOfDayNumber = c.day

        val nameOfDayString : String
        when(nameOfDayNumber){
            1 -> nameOfDayString = "Hetfo"
            2 -> nameOfDayString = "Kedd"
            3 -> nameOfDayString = "Szerda"
            4 -> nameOfDayString = "Csutortok"
            5 -> nameOfDayString = "Pentek"
            6 -> nameOfDayString = "Szombat"
            0 -> nameOfDayString = "Vasarnap"
            else -> nameOfDayString = "Nem jol szamolom a napokat"
        }

        if(month+1 < 10 && day<10)
            tvDate.text = "${year}. 0${month}. 0${day}.\n${nameOfDayString}"
        else if(month+1 < 10)
            tvDate.text = "${year}. 0${month}. ${day}.\n${nameOfDayString}"
        else if(day < 10)
            tvDate.text = "${year}. ${month}. 0${day}.\n${nameOfDayString}"
        else
            tvDate.text = "${year}. ${month}. ${day}.\n${nameOfDayString}"
    }

    private fun loadItemsInBackground() {
        var from: Date = Date()
        var to: Date = Date()
        from.year= pageDate.year
        from.month= pageDate.month
        from.date= pageDate.date
        from.hours=0
        to.year= pageDate.year
        to.month= pageDate.month
        to.date= pageDate.date
        to.hours=24
        thread {
            val doneItems = taskDatabase.exerciseDao().getDoneTasksForDate(from, to)
            val toDoItems = taskDatabase.exerciseDao().getToDoTasksForDate(from, to)
            requireActivity().runOnUiThread {
                adapterDone.update(doneItems)
                adapterToDo.update(toDoItems)
            }
            if(foodDatabase.foodDao().numOfFood(from, to)>0) {
                cal.text = foodDatabase.foodDao().getAllCaloriesForDate(from, to).toString()
                prot.text = foodDatabase.foodDao().getAllProteinForDate(from, to).toString()
                carb.text = foodDatabase.foodDao().getAllCarbohydrateForDate(from, to).toString()
            }
            else{
                cal.text = "0"
                prot.text = "0"
                carb.text = "0"
            }
            if(bodyWeightDatabase.bodyWeightDao().numOfWeights(from, to)>0) {
                var bodyWeights = bodyWeightDatabase.bodyWeightDao().getBodyWeightsForDate(from, to)
                var latestBodyWeight = bodyWeights.get(bodyWeights.size-1)
                kilo.text = latestBodyWeight.weight.toString()
            }
            else{
                kilo.text = "0"
            }
        }
    }

    override fun onToDoClick(item: Exercise, position: Int) {
        thread {
            val doneId = item.id
            val doneTask = item.task
            val doneDate = item.date
            taskDatabase.exerciseDao().deleteItem(item)
            adapterToDo.removeToDo(item)
            val newDoneTask = Exercise(
                id = doneId,
                task = doneTask,
                isDone = Integer(1),
                date = doneDate
            )
            requireActivity().runOnUiThread {
                adapterDone.addDone(newDoneTask)
            }
            taskDatabase.exerciseDao().insert(newDoneTask)
            loadItemsInBackground()
        }
    }

    override fun deleteToDo(item: Exercise) {
        thread {
            adapterToDo.removeToDo(item)
            taskDatabase.exerciseDao().deleteItem(item)
            loadItemsInBackground()
        }
    }

    override fun onDoneClick(item: Exercise, position: Int) {
        thread {
            val toDoId = item.id
            val toDoTask = item.task
            val toDoDate = item.date
            taskDatabase.exerciseDao().deleteItem(item)
            adapterDone.removeDone(item)
            val newToDoTask = Exercise(
                id = toDoId,
                task = toDoTask,
                isDone = Integer(0),
                date = toDoDate
            )
            requireActivity().runOnUiThread {
                adapterToDo.addToDo(newToDoTask)
            }
            taskDatabase.exerciseDao().insert(newToDoTask)
            loadItemsInBackground()
        }
    }

    override fun deleteDone(item: Exercise) {
        thread {
            adapterDone.removeDone(item)
            taskDatabase.exerciseDao().deleteItem(item)
            loadItemsInBackground()
        }
    }
}