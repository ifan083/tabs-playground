package com.example.tabs.inpayment.data

import kotlinx.coroutines.delay

data class Accounts(
  val debitAccounts: List<DebitAccount>,
  val creditAccounts: List<CreditAccount>
)

object DataSource {
  
  private val debitAccounts = listOf(
    DebitAccount("Privatkonto1", "CHF", "1'000"),
    DebitAccount("Privatkonto2", "CHF", "2'000"),
    DebitAccount("Privatkonto3", "EUR", "3'000")
  )
  
  private val creditAccounts =
    listOf(
      CreditAccount("1234 5678 9012 3456", "1'000"),
      CreditAccount("7890 1234 5678 9012", "2'000")
    )
  
  suspend fun accounts(): Accounts {
    delay(3000L)
    return Accounts(debitAccounts = debitAccounts, creditAccounts = creditAccounts)
  }
  
  
}