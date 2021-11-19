package com.example.tabs

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabs.inpayment.presentation.InPaymentViewModel
import com.example.tabs.inpayment.presentation.InPaymentScreen
import com.example.tabs.ui.theme.TabsTheme

class MainActivity : AppCompatActivity() {
  
  private val viewModel = InPaymentViewModel()
  
  @ExperimentalComposeUiApi
  @ExperimentalMaterialApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TabsTheme {
        InPaymentScreen(
          onOrderChanged = viewModel::onTabChanged,
          content = viewModel.uiState
        )
      }
    }
  }
}