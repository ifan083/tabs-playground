package com.example.tabs.inpayment.data

import android.text.format.DateFormat
import java.util.*

data class DebitAccount(
  val accountName: String,
  val currency: String,
  val balance: String
)

data class CreditAccount(
  val accountNr: String,
  val balance: String
)

class ExecutionDate(
  private val dateTime: Long
) {
  fun asString(): String {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = dateTime
    return "${calendar.get(Calendar.DAY_OF_WEEK)}, " + DateFormat.format("dd.MM.yyyy", calendar)
      .toString()
  }
}

