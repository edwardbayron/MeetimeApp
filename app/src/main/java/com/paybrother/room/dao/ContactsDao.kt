package com.paybrother.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.paybrother.room.Contact

@Dao
interface ContactsDao {
    @Query("Select * from contact")
    fun getcontactList() : List<Contact>
}