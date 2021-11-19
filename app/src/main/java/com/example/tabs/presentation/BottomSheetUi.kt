package com.example.tabs.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tabs.domain.Outcome
import com.example.tabs.domain.AnnotatedTextData
import com.example.tabs.domain.TextData

sealed class Sheet {
  
  /**
   *
   */
  class Chooser<T>(
    val selected: T?,
    val available: List<T>,
    val onSelected: (T) -> Unit,
    val headerHolder: (@Composable () -> Unit)? = null,
    val itemHolder: @Composable (T, Boolean) -> Unit
  ) : Sheet()
  
  class Error(val outcome: Outcome.Error) : Sheet()
}


@Composable
fun ErrorSheet(sheet: Sheet.Error, closeSheet: () -> Unit) {
  //todo
}

@Composable
fun <T> ChooserSheet(
  sheet: Sheet.Chooser<T>,
  closeSheet: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    sheet.headerHolder?.let { it() }
    
    sheet.available.forEach {
      Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
          sheet.onSelected(it)
          closeSheet()
        }) {
        sheet.itemHolder(it, sheet.selected == it)
      }
    }
  }
}

@Composable
fun EmptySheet() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(1.dp)
  )
}

val DefaultChooserTextItemHolder: @Composable (TextData, Boolean) -> Unit =
  { data, isSelected ->
    val annotatedData = AnnotatedTextData(
      topLeft = AnnotatedString(data.topLeft),
      topRight = data.topRight?.let { AnnotatedString(it) },
      bottomLeft = data.bottomLeft?.let { AnnotatedString(it) },
      bottomRight = data.bottomRight?.let { AnnotatedString(it) }
    )
    DefaultChooserAnnotatedTextItemHolder(annotatedData, isSelected)
  }

@Preview
@Composable
fun DefaultChooserTextItemHolderPreview() {
  DefaultChooserTextItemHolder(
    TextData(
      topLeft = "topLeft",
      topRight = "topRight",
      bottomLeft = "bottomLeft",
      bottomRight = "bottomRight"
    ),
    false
  )
}

val DefaultChooserAnnotatedTextItemHolder: @Composable (AnnotatedTextData, Boolean) -> Unit =
  { data, isSelected ->
    val backgroundColor = if (isSelected) Color.Gray else Color.Transparent
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = backgroundColor)
    ) {
      Row {
        Text(modifier = Modifier.weight(1f), text = data.topLeft)
        data.topRight?.let {
          Text(modifier = Modifier.weight(1f), text = it, textAlign = TextAlign.End)
        }
      }
      
      Row {
        data.bottomLeft?.let {
          Text(modifier = Modifier.weight(1f), text = it)
        }
        data.bottomRight?.let {
          Text(modifier = Modifier.weight(1f), text = it, textAlign = TextAlign.End)
        }
      }
    }
  }

@Preview
@Composable
fun DefaultChooserAnnotatedTextItemHolderPreview() {
  DefaultChooserAnnotatedTextItemHolder(
    AnnotatedTextData(
      topLeft = buildAnnotatedString {
        append("top")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Left") }
      },
      topRight = buildAnnotatedString {
        append("top")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Right") }
      },
      bottomLeft = buildAnnotatedString {
        append("bottom")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Left") }
      },
      bottomRight = buildAnnotatedString {
        append("bottom")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Right") }
      }
    ),
    false
  )
}