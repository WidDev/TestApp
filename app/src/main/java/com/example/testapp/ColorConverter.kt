package com.example.testapp.utils.converters

import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun toLong(col: ULong) : Long {
        return col.toLong()
    }
    @TypeConverter
    fun toColor(long: Long) : ULong {
        return long.toULong()
    }
}