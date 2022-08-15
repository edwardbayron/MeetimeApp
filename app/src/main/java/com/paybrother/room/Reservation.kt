package com.paybrother.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Reservation")
data class Reservation(@PrimaryKey(autoGenerate = true)
                       var id: Long? = null,
                       @ColumnInfo(name = "name")
                       var name: String,
                       @ColumnInfo(name = "date")
                       var date: String)