package room106.app.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import room106.app.schedule.databinding.DayBinding
import room106.app.schedule.databinding.FragmentDaysBinding
import room106.app.schedule.other.DaysListAdapter
import room106.app.schedule.other.OnDayClickListener
import room106.app.schedule.ui.taskslist.fragments.setDivider
import java.util.*

class DaysFragment(private val onDayClickListener: OnDayClickListener):
    Fragment(R.layout.day) {

    //region Binding
    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dates = createListDate(0, 9)
        val adapter = DaysListAdapter(dates, onDayClickListener)
        binding.rvDays.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvDays.adapter = adapter
        binding.rvDays.setDivider(requireContext(), R.drawable.days_divider, DividerItemDecoration.HORIZONTAL)
    }

    private fun createListDate(start: Int, end: Int): List<Date> {
        if (start >= end) return listOf()

        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, start - 1)
        }

        return ArrayList<Date>().apply {
            for (i in start..end) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                add(calendar.time)
            }
        }
    }
}