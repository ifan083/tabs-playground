package com.example.tabs.inpayment.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingOrder() {
  Column {
    LinearProgressIndicator(
      modifier = Modifier.fillMaxWidth(),
      color = Color.Yellow
    )
    repeat((1..4).count()) { LoadingItem() }
  }
}

@Composable
fun LoadingItem() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    LinearProgressIndicator(
      modifier = Modifier
        .weight(0.52f)
        .height(24.dp)
        .clip(RoundedCornerShape(12.dp)),
      color = Color.Gray
    )
    Spacer(
      modifier = Modifier
        .weight(0.32f)
        .height(24.dp),
    )
    LinearProgressIndicator(
      modifier = Modifier
        .weight(0.16f)
        .height(24.dp)
        .clip(RoundedCornerShape(12.dp)),
      color = Color.Gray
    )
  }
}

@Preview
@Composable
fun LoadingOrderPreview() {
  LoadingOrder()
}