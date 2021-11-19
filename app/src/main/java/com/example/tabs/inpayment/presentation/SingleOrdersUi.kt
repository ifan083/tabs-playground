package com.example.tabs.inpayment.presentation

import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.tabs.domain.TextData
import com.example.tabs.inpayment.domain.SingleOrdersUseCase
import com.example.tabs.presentation.DatePickerItem
import com.example.tabs.presentation.DefaultChooserTextItemHolder
import com.example.tabs.presentation.DropDownItem
import com.example.tabs.presentation.Sheet
import com.google.android.material.datepicker.MaterialDatePicker

@ExperimentalComposeUiApi
@Composable
fun SingleOrdersContent(
  modifier: Modifier = Modifier,
  openSheet: (Sheet) -> Unit,
  closeSheet: () -> Unit,
  content: SingleOrdersUseCase
) {
  val activity = LocalContext.current as AppCompatActivity
  val get = content.get.value
  Column(modifier = modifier
    .fillMaxWidth()
    .fillMaxHeight()
    .pointerInteropFilter {
      if (it.action == MotionEvent.ACTION_DOWN) {
        closeSheet()
      }
      false
    }) {
    AmountAndCurrency(
      openSheet = openSheet,
      selectedCurrency = "CHF",
      allCurrencies = listOf("CHF", "EUR"),
      amount = get.amount,
      onAmountChange = content::onAmountChanged,
      onCurrencyChanged = {}
    )
    DropDownItem(
      modifier = Modifier.clickable {
        openSheet(
          Sheet.Chooser(
            selected = get.selectedDebitAccount,
            available = get.debitAccountList,
            onSelected = content::onSelectedDebitAccount,
            itemHolder = DefaultChooserTextItemHolder
          )
        )
      },
      item = {
        DefaultChooserTextItemHolder(get.selectedDebitAccount, false)
      }
    )
    
    DropDownItem(
      modifier = Modifier.clickable {
        openSheet(
          Sheet.Chooser(
            selected = get.selectedCreditAccount,
            available = get.creditAccountList,
            onSelected = content::onSelectedCreditAccount,
            itemHolder = SpecificChooserItem
          )
        )
      },
      item = {
        val itemData = get.selectedCreditAccount ?: TextData("Credit", bottomLeft = "Not set")
        DefaultChooserTextItemHolder(itemData, false)
      }
    )
    DatePickerItem(
      modifier = Modifier.clickable {
        openDatePicker(activity = activity, onDateChanged = {
          Log.e("DatePicker", "New date: $it")
        })
      },
      item = {
        Text(
          buildAnnotatedString {
            append("Due Date\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
              append("Monday, 04.10.2019")
            }
          }
        )
      }
    )
  }
}

fun openDatePicker(
  activity: AppCompatActivity,
  onDateChanged: (Long) -> Unit
) {
  val picker = MaterialDatePicker.Builder.datePicker().build()
  picker.show(activity.supportFragmentManager, picker.toString())
  picker.addOnPositiveButtonClickListener { onDateChanged(it) }
}

val SpecificChooserItem: @Composable (TextData, Boolean) -> Unit =
  { data, isSelected ->
    val bkgColor = if (isSelected) Color.Red else Color.Transparent
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(bkgColor)
    ) {
      Text(
        text = data.topLeft,
        modifier = Modifier.weight(0.25f),
        textAlign = TextAlign.Center
      )
      data.topRight?.let {
        Divider(
          modifier = Modifier
            .width(1.dp)
            .height(50.dp)
        )
        Text(
          text = it,
          modifier = Modifier.weight(0.5f),
          textAlign = TextAlign.Center
        )
      }
      data.bottomLeft?.let {
        Divider(
          modifier = Modifier
            .width(1.dp)
            .height(50.dp)
        )
        Text(
          text = it,
          modifier = Modifier.weight(0.25f),
          textAlign = TextAlign.Center
        )
      }
    }
  }

//@ExperimentalComposeUiApi
//@Preview
//@Composable
//fun SingleOrdersPreview() {
//  val get = remember {
//    mutableStateOf(
//      SingleViewState(
//        selectedCreditAccount = TextData(""),
//        selectedDebitAccount = TextData(""),
//        debitAccountList = listOf(
//          TextData("Debit", "AccountName1", "currency", "balance")
//        ),
//        creditAccountList = listOf(
//          TextData("Credit", "AccountNr", "balance")
//        )
//      )
//    )
//  }
//
//  SingleOrdersContent(
//    openSheet = {},
//    closeSheet = {},
//    content = SingleOrdersUseCase(
//    )
//  )
//}