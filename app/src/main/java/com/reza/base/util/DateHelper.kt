package com.reza.base.util

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    const val DDMMMMYYYY = "dd MMMM yyyy"
    const val EEDDMMMYYYY_HHMMZZZ = "EEE, dd MMM yyyy - HH:mm zzz"

    fun dateMessageFormat(timemillis: Long, newFormat: String): String {
        val sdf = SimpleDateFormat(newFormat, Locale("in"))
        val mDate = Date(timemillis)
        return sdf.format(mDate)
    }
}