package room106.app.schedule.other

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateConversion {

    private const val DAY_PATTERN = "MM/dd/yyyy"

    @SuppressLint("SimpleDateFormat")
    fun toString(date: Date): String {
        val pattern = SimpleDateFormat(DAY_PATTERN)
        return pattern.format(date)
    }

    fun today() = toString(Calendar.getInstance().time)
}