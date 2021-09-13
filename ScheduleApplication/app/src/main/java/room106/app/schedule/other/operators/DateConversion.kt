package room106.app.schedule.other.operators

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

    fun createListDate(start: Int, end: Int): List<Date> {
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