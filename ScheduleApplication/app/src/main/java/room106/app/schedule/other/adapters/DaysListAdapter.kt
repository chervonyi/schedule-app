package room106.app.schedule.other.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import room106.app.schedule.R
import room106.app.schedule.databinding.DayBinding
import room106.app.schedule.other.listeners.OnDayClickListener
import java.text.SimpleDateFormat
import java.util.*

class DaysListAdapter(
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

        holder.addOnDayClickListener(onDayClickListener, position)
        holder.setHighlight(position == selectedId)
    }

    override fun getItemCount() = days.size
}