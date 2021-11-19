package com.example.tabs.inpayment.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusEventModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabs.domain.TextData
import com.example.tabs.presentation.DefaultChooserTextItemHolder
import com.example.tabs.presentation.Sheet

data class AmountData(
  val amount: TextFieldValue,
  val hint: String? = null,
  val error: String? = null
)

@Composable
fun AmountAndCurrency(
  modifier: Modifier = Modifier,
  openSheet: (Sheet) -> Unit,
  selectedCurrency: String,
  allCurrencies: List<String>,
  onCurrencyChanged: (String) -> Unit,
  amount: AmountData,
  onAmountChange: (TextFieldValue) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    
    var isFocused: Boolean = remember { false }
    
    val hintColor = if (amount.error == null) Color.DarkGray else Color.Red
    val dividerColor = when {
      isFocused && amount.error == null -> MaterialTheme.colors.primary
      isFocused && amount.error != null -> Color.Red
      else -> Color.DarkGray
    }
    val hintText = amount.error ?: amount.hint
    
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = selectedCurrency, modifier = Modifier
        .clickable {
          openSheet(
            Sheet.Chooser(
              selected = TextData(selectedCurrency),
              available = allCurrencies.map { TextData(it) },
              onSelected = { onCurrencyChanged(it.topLeft) },
              itemHolder = DefaultChooserTextItemHolder
            )
          )
        }
        .padding(8.dp))
      
      TextField(
        modifier = Modifier
          .weight(1f)
          .onFocusChanged {
            isFocused = it.isFocused
            Log.e("Focus change", isFocused.toString())
          }
          .onFocusEvent {
            Log.e("FocusEvent", it.toString())
          },
        value = amount.amount,
        onValueChange = { tfv: TextFieldValue ->
          Log.e("Selection", tfv.selection.toString())
          
          if (tfv.text.length < 18) {
            onAmountChange(tfv)
          }
        },
        textStyle = androidx.compose.ui.text.TextStyle(
          fontSize = 40.sp,
          textAlign = TextAlign.End
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
          backgroundColor = Color.Transparent,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        )
      )
    }
    Text(
      text = hintText!!,
      fontSize = 12.sp,
      color = hintColor
    )
    Divider(
      modifier = modifier
        .fillMaxWidth()
        .height(1.dp),
      color = dividerColor
    )
  }
}

@Preview
@Composable
fun AmountAndCurrencyPreview() {
  AmountAndCurrency(
    openSheet = {},
    selectedCurrency = "CHF",
    allCurrencies = listOf("CHF", "EUR"),
    amount = AmountData(TextFieldValue("1'000")),
    onAmountChange = {},
    onCurrencyChanged = {}
  )
}
