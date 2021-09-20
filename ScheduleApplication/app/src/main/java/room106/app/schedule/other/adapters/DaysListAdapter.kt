package room106.app.schedule.other.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import room106.app.schedule.R
import room106.app.schedule.data.db.entities.Task
import room106.app.schedule.databinding.DayBinding
import room106.app.schedule.other.listeners.OnDayClickListener
import room106.app.schedule.other.operators.DateConversion
import room106.app.schedule.other.views.DayHintPoint
import java.text.SimpleDateFormat
import java.util.*

class DaysListAdapter(
    var tasks: List<Task>,
    private val days: List<Date>,
    private val onDayClickListener: OnDayClickListener
): RecyclerView.Adapter<DaysListAdapter.DayViewHolder>() {

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DayBinding.bind(itemView)

        fun addOnDayClickListener(listener: OnDayClickListener, id: Int) {
            itemView.setOnClickListener {
                notifyItemChanged(selectedId)
                notifyItemChanged(id)
                selectedId = id
                listener.onSelectDayListener(days[id])
            }
        }

        fun setBottomHint(num: Int) {
            binding.llHint.removeAllViews()
            for (i in 0 until num) {
                if (i > 2) return
                binding.llHint.addView(DayHintPoint(itemView.context))
            }
        }

        fun setHighlight(isHighlight: Boolean) {
            if (isHighlight) {
                binding.bDay.background = ContextCompat.getDrawable(itemView.context, R.drawable.selected_day)
                binding.bDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.accent_font))
            } else {
                binding.bDay.background = ContextCompat.getDrawable(itemView.context, R.drawable.day)
                binding.bDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.font))
            }
        }
    }

    private var selectedId = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day, parent, false)
        return DayViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val date = days[position]

        holder.binding.bDay.text = SimpleDateFormat("d").format(date)
        holder.binding.tvWeek.text = SimpleDateFormat("EEE").format(date)

//        holder.setIsRecyclable(false)
        holder.addOnDayClickListener(onDayClickListener, position)
        holder.setHighlight(position == selectedId)
        holder.setBottomHint(getTasksCount(date, tasks))
    }

    private fun getTasksCount(date: Date, tasks: List<Task>): Int {
        val day = DateConversion.toString(date)
        val count = tasks.count {
            it.day == day
        }
        return count
    }

    override fun getItemCount() = days.size
}