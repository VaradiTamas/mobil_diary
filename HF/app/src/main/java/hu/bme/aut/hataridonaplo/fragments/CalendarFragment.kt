package hu.bme.aut.hataridonaplo.fragments

import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.hataridonaplo.R
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar){
    private lateinit var calendarView: CalendarView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = view.findViewById(R.id.calendar)

        calendarView.setOnDateChangeListener(object : OnDateChangeListener {
            override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
                var datum = Date()
                datum.year = year-1900
                datum.month = month
                datum.date = dayOfMonth
                HomeFragment.pageDate = datum
                findNavController().navigate(R.id.action_calendarFragment_to_homeFragment)
            }
        })
    }
}