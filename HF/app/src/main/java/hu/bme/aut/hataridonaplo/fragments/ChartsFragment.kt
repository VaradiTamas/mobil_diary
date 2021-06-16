package hu.bme.aut.hataridonaplo.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import hu.bme.aut.hataridonaplo.R
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeightDatabase
import hu.bme.aut.hataridonaplo.data.food.FoodDatabase
import kotlinx.android.synthetic.main.fragment_charts.*
import java.util.*
import kotlin.collections.ArrayList

class  ChartsFragment : Fragment(R.layout.fragment_charts){

    var barChart: BarChart? = null
    private lateinit var startDate: Date
    private var oneDay: Date = Date(86400000)
    private lateinit var bodyWeightDatabase: BodyWeightDatabase
    private lateinit var foodDatabase: FoodDatabase
    private lateinit var tvStartingDate: TextView
    private lateinit var categorySpinner: Spinner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvStartingDate = view.findViewById(R.id.tvDateChart)
        barChart = view.findViewById(R.id.barChart)

        bodyWeightDatabase = Room.databaseBuilder(requireContext(), BodyWeightDatabase::class.java, "bodyweights")
            .allowMainThreadQueries()
            .build()

        foodDatabase = Room.databaseBuilder(requireContext(), FoodDatabase::class.java, "foods")
            .allowMainThreadQueries()
            .build()

        categorySpinner = view.findViewById(R.id.spinnerCategory)
        categorySpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.categories)
            )
        )

        categorySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position==1)
                    setCaloriesBarChart()
                else
                    setWeightBarChart()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                setWeightBarChart()
            }
        }

        setSartingDate()

        btnLeftChart.setOnClickListener(){
            leftButtonClicked()
        }

        btnRightChart.setOnClickListener(){
            rightButtonClicked()
        }
    }

    fun leftButtonClicked(){
        startDate.time = startDate.time - 6*oneDay.time
        setTitle()
        setWeightBarChart()
    }

    fun rightButtonClicked(){
        startDate.time = startDate.time + 6*oneDay.time
        setTitle()
        setWeightBarChart()
    }

    fun setSartingDate() {
        startDate = Date()
        startDate.time = startDate.time - 6 * oneDay.time
        startDate.hours = 0
        startDate.minutes = 0

        setTitle()
    }

    fun setTitle(){
        val year = startDate.year+1900
        val month = startDate.month+1
        val day = startDate.date

        if(month+1 < 10 && day<10)
            tvStartingDate.text = "Starting date\n${year}. 0${month}. 0${day}."
        else if(month+1 < 10)
            tvStartingDate.text = "Starting date\n${year}. 0${month}. ${day}."
        else if(day < 10)
            tvStartingDate.text = "Starting date\n${year}. ${month}. 0${day}."
        else
            tvStartingDate.text = "Starting date\n${year}. ${month}. ${day}."
    }

    private fun setWeightBarChart() {
        val values = ArrayList<BarEntry>()
        var millisec = startDate.time
        var from = Date(millisec)
        var to = Date(millisec + oneDay.time)
        val daysOfWeek = mutableListOf<String>()

        for (i in 1..7) {
            var day: String
            if(from.month+1 < 10 && from.date<10)
                day = "0${from.month+1}. 0${from.date}."
            else if(from.month+1 < 10)
                day = "0${from.month+1}. ${from.date}."
            else if(from.date < 10)
                day = "${from.month+1}. 0${from.date}."
            else
                day = "${from.month+1}. ${from.date}."
            daysOfWeek.add(i - 1, day)

            if (bodyWeightDatabase.bodyWeightDao().numOfWeights(from, to) > 0) {
                var bodyWeights =
                    bodyWeightDatabase.bodyWeightDao().getBodyWeightsForDate(from, to)
                var latestBodyWeight = bodyWeights.get(bodyWeights.size - 1)
                values.add(BarEntry(i.toFloat() - 1, latestBodyWeight.weight.toFloat()))
            } else {
                values.add(BarEntry(i.toFloat() - 1, 0f))
            }

            from.time = from.time + oneDay.time
            to.time = to.time + oneDay.time
        }

        val barDataSet = BarDataSet(values, "Testsuly")
        val data = BarData(barDataSet)
        barChart?.data = data

        val formatter: IAxisValueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return daysOfWeek[(value.toInt())]
            }
        }

        val xAxis: XAxis = barChart!!.xAxis
        xAxis.valueFormatter = formatter
        barDataSet.color = resources.getColor(R.color.colorPrimary)

        barChart?.animateY(3000)
        barChart?.description?.text = "Testsuly gyarapodasa"
        barChart?.description?.textSize = 14f
    }

    private fun setCaloriesBarChart() {
        val values = ArrayList<BarEntry>()
        var millisec = startDate.time
        var from = Date(millisec)
        var to = Date(millisec + oneDay.time)
        val daysOfWeek = mutableListOf<String>()

        for (i in 1..7) {
            var day: String
            if(from.month+1 < 10 && from.date<10)
                day = "0${from.month+1}. 0${from.date}."
            else if(from.month+1 < 10)
                day = "0${from.month+1}. ${from.date}."
            else if(from.date < 10)
                day = "${from.month+1}. 0${from.date}."
            else
                day = "${from.month+1}. ${from.date}."
            daysOfWeek.add(i - 1, day)

            if (foodDatabase.foodDao().numOfFood(from, to) > 0) {
                values.add(BarEntry(i.toFloat() - 1, foodDatabase.foodDao().getAllCaloriesForDate(from, to).toFloat()))
            } else {
                values.add(BarEntry(i.toFloat() - 1, 0f))
            }

            from.time = from.time + oneDay.time
            to.time = to.time + oneDay.time
        }

        val barDataSet = BarDataSet(values, "Karoliaszam")
        val data = BarData(barDataSet)
        barChart?.data = data

        val formatter: IAxisValueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return daysOfWeek[(value.toInt())]
            }
        }

        val xAxis: XAxis = barChart!!.xAxis
        xAxis.valueFormatter = formatter
        barDataSet.color = resources.getColor(R.color.colorPrimary)

        barChart?.animateY(3000)
        barChart?.description?.text = "Kaloriabevitel eloszlasa"
        barChart?.description?.textSize = 14f
    }
}