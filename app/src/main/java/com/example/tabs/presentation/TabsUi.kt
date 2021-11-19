package com.example.tabs.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class OrderTabs(val value: String) {
  Single("Single Orders"),
  Standing("Standing Orders")
}

@Composable
fun TabContainer(
  modifier: Modifier = Modifier,
  selected: OrderTabs,
  tabs: List<OrderTabs>,
  onTabSelected: (OrderTabs) -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(color = MaterialTheme.colors.primary)
      .padding(12.dp)
  ) {
    Row(
      modifier = modifier
        .fillMaxWidth()
        .background(color = Color.DarkGray)
        .padding(2.dp)
    ) {
      tabs.forEach {
        val buttonColor = if (selected == it) MaterialTheme.colors.primary else Color.DarkGray
        val buttonBorder = if (selected == it) BorderStroke(2.dp, Color.DarkGray) else null
        Button(modifier = modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
          border = buttonBorder,
          onClick = { onTabSelected(it) }
        ) {
          Text(text = it.value)
        }
      }
    }
  }
}

@Preview
@Composable
fun TabContainerPreview() {
  TabContainer(
    modifier = Modifier,
    tabs = OrderTabs.values().asList(),
    selected = OrderTabs.Single,
    onTabSelected = {}
  )
}