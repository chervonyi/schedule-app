package room106.app.schedule.other.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import room106.app.schedule.R

class DayHintPoint(context: Context): View(context) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.day_bottom_hint)
        val size = context.resources.getDimension(R.dimen.day_bottom_hint_size).toInt()
        val param = ViewGroup.LayoutParams(size, size)
        layoutParams = param
    }
}