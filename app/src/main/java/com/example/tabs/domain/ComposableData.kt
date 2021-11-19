package com.example.tabs.domain

import androidx.compose.ui.text.AnnotatedString

data class TextData(
  val topLeft: String,
  val topRight: String? = null,
  val bottomLeft: String? = null,
  val bottomRight: String? = null,
)

data class AnnotatedTextData(
  val topLeft: AnnotatedString,
  val topRight: AnnotatedString? = null,
  val bottomLeft: AnnotatedString? = null,
  val bottomRight: AnnotatedString? = null
)

sealed class Content<out T> {
  object Loading : Content<Nothing>()
  class Data<T>(val value: T) : Content<T>()
}