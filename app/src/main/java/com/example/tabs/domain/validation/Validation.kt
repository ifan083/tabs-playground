package com.example.tabs.domain.validation

interface Validation<I> {
  fun isValid(input: I): Boolean
}

class InRange(private val min: Long, private val max: Long) : Validation<Long> {
  override fun isValid(input: Long): Boolean = input in min..max
}