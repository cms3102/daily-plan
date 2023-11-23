package com.sergio.simpleplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.sergio.simpleplanner.ui.SimplePlannerApp
import com.sergio.common.theme.SimplePlannerTheme
import com.sergio.simpleplanner.state.MainState
import com.sergio.simpleplanner.state.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SimplePlannerTheme {
                SimplePlannerApp()
            }
        }
    }

    private fun ComponentActivity.setSplash() {
        val splash = installSplashScreen()
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                splash.setKeepOnScreenCondition {
                    when(state) {
                        MainState.Loading -> true
                        MainState.Success -> false
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimplePlannerTheme {
        SimplePlannerApp()
    }
}