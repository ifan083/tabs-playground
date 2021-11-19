package com.example.tabs.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FormItem(
  modifier: Modifier = Modifier,
  iconRes: ImageVector,
  content: @Composable () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
  ) {
    Row(
      modifier = modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(modifier = modifier.weight(1f)) {
        content()
      }
      Icon(
        imageVector = iconRes,
        contentDescription = "",
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Divider(modifier = Modifier.fillMaxWidth())
  }
}

@Composable
fun DropDownItem(
  modifier: Modifier = Modifier,
  item: @Composable () -> Unit
) {
  FormItem(modifier = modifier, iconRes = Icons.Outlined.ArrowDropDown, content = item)
}

@Composable
fun DatePickerItem(
  modifier: Modifier = Modifier,
  item: @Composable () -> Unit
) {
  FormItem(modifier = modifier, iconRes = Icons.Outlined.DateRange, content = item)
}

@Preview
@Composable
fun DropDownItemPreview() {
  DropDownItem {
    Text(text = "dummy text")
  }
}