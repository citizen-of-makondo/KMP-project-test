package org.aleksandrilinskii.nutrisport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.aleksandrilinskii.navigation.NavGraph
import com.aleksandrilinskii.navigation.Screen
import com.aleksandrilinskii.nutrisport.shared.Constants
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.aleksandrilinskii.data.domain.CustomerRepository
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val customerRepository = koinInject<CustomerRepository>()
        var appReady by remember { mutableStateOf(false) }
        val isUserAuthenticated = remember { customerRepository.getCurrentUserId() != null }
        val startDestination by remember {
            mutableStateOf(
                if (isUserAuthenticated) {
                    Screen.HomeGraph
                } else Screen.Auth
            )
        }

        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(GoogleAuthCredentials(Constants.WEB_CLIENT_ID))
            appReady = true
        }

        AnimatedVisibility(
            visible = appReady,
            modifier = Modifier.fillMaxSize()
        ) {
            NavGraph(
                startDestination = startDestination
            )
        }
    }
}