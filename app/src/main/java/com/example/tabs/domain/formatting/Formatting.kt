package com.example.tabs.domain.formatting

interface Formatting<I, O> {
  fun strip(output: O): I
  fun apply(input: I): O
}

class ThousandsDelimiter : Formatting<Long, String> {
  
  override fun strip(output: String): Long =
    output.replace("'", "").toLong()
  
  override fun apply(input: Long): String {
    val inputAsString = input.toString()
    val from = inputAsString.length % 3
    val temp = inputAsString.mapIndexed { index, char ->
      if ((index + 1) % 3 == from) "$char'"
      else "$char"
    }.joinToString(separator = "") { it }
    return if (temp.endsWith("'")) temp.substring(0, temp.length - 1)
    else temp
  }
}