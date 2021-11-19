package com.example.tabs.inpayment.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.tabs.domain.TextData
import com.example.tabs.inpayment.data.Accounts
import com.example.tabs.inpayment.data.CreditAccount
import com.example.tabs.inpayment.data.DebitAccount
import com.example.tabs.inpayment.presentation.AmountData

data class SingleViewState(
  val amount: AmountData,
  val selectedDebitAccount: TextData,
  val debitAccountList: List<TextData>,
  val selectedCreditAccount: TextData,
  val creditAccountList: List<TextData>,
  val executionDate: TextData? = null,
)

class SingleOrdersUseCase(accounts: Accounts) : Order {
  
  private val hintText = "Please consider your maximum inpayment amounts."
  private val initialAmountValue = "1'000"
  
  val get = mutableStateOf(SingleViewState(
    amount = AmountData(
      amount = TextFieldValue(
        text = initialAmountValue,
        selection = TextRange(initialAmountValue.length)
      ),
      hint = hintText
    ),
    selectedDebitAccount = debitAccountNotSet(),
    selectedCreditAccount = creditAccountNotSet(),
    debitAccountList = accounts.debitAccounts.map { toTextData(it) },
    creditAccountList = accounts.creditAccounts.map { toTextData(it) }
  ))
  
  fun onSelectedDebitAccount(textData: TextData) {
    if (get.value.selectedDebitAccount != textData) {
      get.value = get.value.copy(selectedDebitAccount = textData)
    }
  }
  
  fun onSelectedCreditAccount(textData: TextData) {
    if (get.value.selectedCreditAccount != textData) {
      get.value = get.value.copy(selectedCreditAccount = textData)
    }
  }
  
  fun onAmountChanged(newAmount: TextFieldValue) {
    if (get.value.amount.amount != newAmount) {
      //strip formatting
      
      if (newAmount.text.isBlank()) {
        get.value = get.value
          .copy(amount = AmountData(newAmount, hintText))
        return
      }
      
      val amountVal = newAmount.text.replace("'", "").toLong()
      val oldSelection = get.value.amount.amount.selection
      val oldLength = newAmount.text.length
      val isOldCursorLast =
        oldSelection.start == (oldLength + 1) && oldSelection.end == (oldLength + 1)
      Log.e("Cursor", "isLast=$isOldCursorLast")
      
      //validate
      val error = if (amountVal > 5000) "Amount over 5'000, please fix" else null
      
      //apply formatting
      val formattedVal = thousands(amountVal)
      
      get.value = get.value.copy(
        amount = AmountData(
          amount = newAmount.copy(
            text = formattedVal
//            selection = if (isOldCursorLast) TextRange(formattedVal.length) else oldSelection
          ),
          error = error,
          hint = hintText
        )
      )
      
    }
  }
  
  private fun thousands(input: Long): String {
    val inputAsString = input.toString()
    val from = inputAsString.length % 3
    val temp = inputAsString.mapIndexed { index, char ->
      if ((index + 1) % 3 == from) "$char'"
      else "$char"
    }.joinToString(separator = "") { it }
    return if (temp.endsWith("'")) temp.substring(0, temp.length - 1)
    else temp
  }
  
  //data execution
  fun onSubmit() {
  
  }
  
  private fun debitAccountNotSet() = TextData(
    topLeft = "Debit",
    bottomLeft = "Not Set"
  )
  
  private fun creditAccountNotSet() = TextData(
    topLeft = "Credit",
    bottomLeft = "Not Set"
  )
  
  //data formatting
  private fun toTextData(data: DebitAccount) = TextData(
    topLeft = "Debit",
    topRight = data.accountName,
    bottomLeft = data.currency,
    bottomRight = data.balance
  )
  
  private fun toTextData(data: CreditAccount) = TextData(
    topLeft = "Credit",
    topRight = data.accountNr,
    bottomLeft = data.balance
  )
}