package com.example.tabs.inpayment.domain

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.tabs.domain.Requirement
import com.example.tabs.domain.TextData
import com.example.tabs.domain.formatting.ThousandsDelimiter
import com.example.tabs.domain.validation.InRange
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
      textFieldValue = TextFieldValue(
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
  
  private val amountInputRequirement = Requirement(
    formatting = ThousandsDelimiter(),
    validity = listOf(InRange(1000, 5000))
  )
  
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
    val isCursorChange = get.value.amount.textFieldValue.text == newAmount.text
    if (isCursorChange) {
      get.value = get.value.copy(
        amount = get.value.amount.copy(textFieldValue = newAmount)
      )
      return
    }
    
    if (newAmount.text.isBlank()) {
      //empty string
      get.value = get.value
        .copy(amount = get.value.amount.copy(textFieldValue = newAmount))
      return
    }
    
    val result = amountInputRequirement.check(newAmount.text)
    
    val oldSelection = get.value.amount.textFieldValue.selection
    val oldSelectionAtEnd = oldSelection.start == oldSelection.end
        && oldSelection.start == get.value.amount.textFieldValue.text.length
    
    val newSelection = if (oldSelectionAtEnd) TextRange(result.value.length)
    else newAmount.selection
    
    get.value = get.value.copy(
      amount = get.value.amount.copy(
        textFieldValue = newAmount.copy(
          text = result.value,
          selection = newSelection
        ),
        error = if (result.error != null) "Amount over 5'000, try below" else null
      )
    )
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