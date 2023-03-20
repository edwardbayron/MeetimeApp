package com.paybrother.data

import java.util.*

data class LoanData(val id: String, val title: String, val sum: Int, val date: Date)

data class LoanParcelable(val id: String, val title: String, val sum: Int, val date: Date) : java.io.Serializable{

}
