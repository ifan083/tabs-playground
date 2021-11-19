package com.example.tabs.inpayment.domain

interface Formatting<I,O> {
  fun <I, O> strip(o : O) : I
  fun <I,O> apply(i : I) : O
}

class ThousandsDelimiter : Formatting<Int, String> {
  override fun <I, O> strip(o: O): I {
    TODO("Not yet implemented")
  }
  
  override fun <I, O> apply(i: I): O {
    TODO("Not yet implemented")
  }
}