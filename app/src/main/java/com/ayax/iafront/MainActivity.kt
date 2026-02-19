package com.ayax.iafront

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ayax.iafront.ui.theme.AIFrontTheme

/**
 * Single-activity entry point for the app.
 * Hosts the Compose hierarchy and wires the root ViewModel.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // On older Android versions, edge-to-edge can interfere with IME resize.
        // Keep it for modern devices and use classic resize behavior as fallback.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            enableEdgeToEdge()
        }
        setContent {
            AIFrontTheme {
                Surface {
                    val vm: ChatViewModel = viewModel(
                        factory = ChatViewModelFactory(applicationContext)
                    )
                    ChatScreen(vm)
                }
            }
        }
    }
}
