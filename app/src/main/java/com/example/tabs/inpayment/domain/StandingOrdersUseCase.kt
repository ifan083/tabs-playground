package com.example.tabs.inpayment.domain

import androidx.compose.runtime.mutableStateOf
import com.example.tabs.inpayment.data.Accounts

data class StandingOrderViewState(
  val data1: String
)

class StandingOrdersUseCase(accounts: Accounts) : Order {
  
  val data = StandingOrderViewState("")
  
}