package com.example.tabs.inpayment.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import com.example.tabs.inpayment.domain.StandingOrdersUseCase
import com.example.tabs.presentation.Sheet


@Composable
fun StandingOrderContent(
  modifier: Modifier = Modifier,
  openSheet: (Sheet) -> Unit,
  closeSheet: () -> Unit,
  content: StandingOrdersUseCase
) {
  Column {
    Text(buildAnnotatedString {
    
    })
    Text(text = "Debit Privatkonto")
    Text(text = "Credit Account nr.")
    Text(text = "Due Date: Monday 04.10.2019")
  }
}

//@Preview
//@Composable
//fun StandingOrderPreview() {
//  val get = remember {
//    mutableStateOf(StandingViewState(""))
//  }
//  StandingOrderContent(
//    openSheet = {},
//    closeSheet = { /*TODO*/ },
//    content = Order.Standing(
//      get = get,
//      onData1Changed = {}
//    )
//  )
//}