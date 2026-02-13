package com.ayax.iafront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ayax.iafront.ui.theme.IAFrontTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IAFrontTheme {
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
