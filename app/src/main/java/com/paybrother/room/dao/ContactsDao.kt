package com.paybrother.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paybrother.room.Contact
import com.paybrother.room.Reservation

@Dao
interface ContactsDao {
    @Query("Select * from contact")
    fun getcontactList() : List<Contact>
    @Insert
    fun insertContact(contact: Contact)
}