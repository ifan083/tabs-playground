package com.example.tabs.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BottomSheetLayout(
  content: @Composable (openSheet: (Sheet) -> Unit, closeSheet: () -> Unit) -> Unit
) {
  val scope = rememberCoroutineScope()
  val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
  )
  var sheet: Sheet? by remember { mutableStateOf(null) }
  val closeSheet: () -> Unit = {
    scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
  }
  val openSheet: (Sheet) -> Unit = {
    sheet = it
    scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
  }
  
  BottomSheetScaffold(
    sheetContent = {
      when (sheet) {
        null -> EmptySheet()
        is Sheet.Chooser<*> -> ChooserSheet(sheet as Sheet.Chooser<*>, closeSheet)
        is Sheet.Error -> ErrorSheet(sheet as Sheet.Error, closeSheet)
      }
    },
    sheetPeekHeight = 0.dp,
    scaffoldState = bottomSheetScaffoldState,
    sheetElevation = 8.dp,
    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
  ) {
    content(openSheet, closeSheet)
  }
}