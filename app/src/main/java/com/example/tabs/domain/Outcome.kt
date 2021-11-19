package com.example.tabs.domain


sealed class Outcome<out T> {
  class Complete<T>(val value: T) : Outcome<T>()
  open class Error(val message: String) : Outcome<Nothing>() {
    class Info(message: String) : Error(message = message)
  }
}