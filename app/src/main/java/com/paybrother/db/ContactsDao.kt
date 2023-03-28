//package com.paybrother.old.room.dao
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Query
//import com.paybrother.old.room.Contact
//import com.paybrother.old.room.Reservation
//
//@Dao
//interface ContactsDao {
//    @Query("Select * from contact")
//    fun getcontactList() : List<Contact>
//    @Insert
//    fun insertContact(contact: Contact)
//    @Delete
//    fun deleteContact(contact: Contact)
//}