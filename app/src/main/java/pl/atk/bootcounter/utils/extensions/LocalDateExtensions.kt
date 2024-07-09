package pl.atk.bootcounter.utils.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.time
}

fun Date.toReadableDateString(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val defaultTimeZone = TimeZone.getDefault()
    dateFormat.timeZone = defaultTimeZone
    return dateFormat.format(this)
}

fun Date.toReadableDateTimeString(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:MM:SS", Locale.getDefault())
    val defaultTimeZone = TimeZone.getDefault()
    dateFormat.timeZone = defaultTimeZone
    return dateFormat.format(this)
}