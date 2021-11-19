package com.example.tabs.inpayment.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabs.domain.Content
import com.example.tabs.inpayment.data.DataSource
import com.example.tabs.inpayment.domain.Order
import com.example.tabs.inpayment.domain.SingleOrdersUseCase
import com.example.tabs.inpayment.domain.StandingOrdersUseCase
import com.example.tabs.presentation.OrderTabs
import kotlinx.coroutines.launch

class InPaymentViewModel : ViewModel() {
  
  var uiState by mutableStateOf<Content<Order>>(Content.Loading)
    private set
  
  private lateinit var singleOrdersUseCase: SingleOrdersUseCase
  private lateinit var standingOrdersUseCase: StandingOrdersUseCase
  
  init {
    viewModelScope.launch {
      val accounts = DataSource.accounts()
      singleOrdersUseCase = SingleOrdersUseCase(accounts)
      standingOrdersUseCase = StandingOrdersUseCase(accounts)
      uiState = Content.Data(singleOrdersUseCase)
    }
  }
  
  fun onTabChanged(newValue: OrderTabs) {
    uiState = when (newValue) {
      OrderTabs.Standing -> Content.Data(standingOrdersUseCase)
      OrderTabs.Single -> Content.Data(singleOrdersUseCase)
    }
  }
}