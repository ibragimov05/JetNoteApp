package com.example.jetnoteapp.util

import androidx.room.TypeConverter
import java.util.Date

class DateConvertor {
    @TypeConverter
    fun timeStampFromDate(date: Date): Long =
        date.time


    @TypeConverter
    fun dateFromTimeStamp(timestamp: Long): Date =
        Date(timestamp)

}