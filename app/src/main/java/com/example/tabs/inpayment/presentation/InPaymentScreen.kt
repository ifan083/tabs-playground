package com.example.tabs.inpayment.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabs.domain.Content
import com.example.tabs.domain.Outcome
import com.example.tabs.inpayment.domain.Order
import com.example.tabs.inpayment.domain.SingleOrdersUseCase
import com.example.tabs.inpayment.domain.StandingOrdersUseCase
import com.example.tabs.presentation.BottomSheetLayout
import com.example.tabs.presentation.OrderTabs
import com.example.tabs.presentation.TabContainer


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun InPaymentScreen(
  modifier: Modifier = Modifier,
  onOrderChanged: (OrderTabs) -> Unit,
  error: Outcome.Error? = null,
  content: Content<Order>
) {
  
  val selectedTab = when (content) {
    is Content.Loading -> OrderTabs.Single
    is Content.Data -> {
      when (content.value) {
        is SingleOrdersUseCase -> OrderTabs.Single
        is StandingOrdersUseCase -> OrderTabs.Standing
        else -> OrderTabs.Single
      }
    }
  }
  
  BottomSheetLayout { openSheet, closeSheet ->
    Column(
      modifier = modifier.fillMaxWidth(),
    ) {
      InPaymentToolbar(
        onBack = {},
        onExit = {}
      )
      TabContainer(
        tabs = OrderTabs.values().toList(),
        selected = selectedTab,
        onTabSelected = onOrderChanged
      )
      when (content) {
        is Content.Loading -> LoadingOrder()
        is Content.Data ->
          when (content.value) {
            is StandingOrdersUseCase -> StandingOrderContent(
              openSheet = openSheet,
              closeSheet = closeSheet,
              content = content.value
            )
            is SingleOrdersUseCase -> SingleOrdersContent(
              openSheet = openSheet,
              closeSheet = closeSheet,
              content = content.value
            )
          }
      }
    }
  }
}

@Composable
fun InPaymentToolbar(
  onBack: () -> Unit,
  onExit: () -> Unit,
) {
  TopAppBar(
    title = { Text(text = "Account 3a inpayment") },
    backgroundColor = MaterialTheme.colors.primary,
    contentColor = MaterialTheme.colors.onPrimary,
    navigationIcon = {
      IconButton(onClick = onBack) {
        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "")
      }
    },
    actions = {
      IconButton(onClick = onExit) {
        Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = "")
      }
    }
  )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview
@Composable
fun TabScreenPreview() {
  InPaymentScreen(
    onOrderChanged = {},
    content = Content.Loading
  )
}