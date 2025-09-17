package com.aleksandrilinskii.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.auth.component.GoogleButton
import com.aleksandrilinskii.nutrisport.shared.Alpha
import com.aleksandrilinskii.nutrisport.shared.BebasNeueFont
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.SurfaceBrand
import com.aleksandrilinskii.nutrisport.shared.SurfaceError
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import com.aleksandrilinskii.nutrisport.shared.TextSecondary
import com.aleksandrilinskii.nutrisport.shared.TextWhite
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@Composable
fun AuthScreen(navigateToHome: () -> Unit) {
    val viewModel = koinViewModel<AuthViewModel>()
    val scope = rememberCoroutineScope()
    val messageBarState = rememberMessageBarState()
    var loadingState by remember {
        mutableStateOf(false)
    }

    Scaffold { paddingValues ->
        Column {
            ContentWithMessageBar(
                contentBackgroundColor = Surface,
                messageBarState = messageBarState,
                errorMaxLines = 2,
                modifier = Modifier.padding(paddingValues),
                errorContainerColor = SurfaceError,
                errorContentColor = TextWhite,
                successContainerColor = SurfaceBrand,
                successContentColor = TextPrimary
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "NUTRISPORT",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontFamily = BebasNeueFont(),
                            fontSize = FontSize.EXTRA_LARGE,
                            color = TextSecondary
                        )

                        Text(
                            "Sign in to continue",
                            modifier = Modifier.fillMaxWidth().alpha(Alpha.HALF),
                            textAlign = TextAlign.Center,
                            fontSize = FontSize.REGULAR,
                            color = TextPrimary
                        )
                    }

                    GoogleButtonUiContainerFirebase(
                        onResult = { result ->
                            result.onSuccess { user ->
                                viewModel.createCustomer(
                                    user = user,
                                    onSuccess = {
                                        scope.launch {
                                            messageBarState.addSuccess("Authenticated as ${user?.email}")
                                            delay(2000)
                                            navigateToHome()
                                        }
                                    },
                                    onError = { error -> messageBarState.addError(error) })
                                loadingState = false
                            }.onFailure { error ->
                                when {
                                    error.message?.contains("A network error") == true ->
                                        messageBarState.addError("No internet connection")

                                    error.message?.contains("Idtoken is null") == true ->
                                        messageBarState.addError("Sign in cancelled")

                                    else -> messageBarState.addError(
                                        error.message ?: "Unknown error"
                                    )
                                }
                                loadingState = false
                            }
                        }) {
                        GoogleButton(
                            loading = loadingState,
                            onClick = {
                                this@GoogleButtonUiContainerFirebase.onClick()
                            }
                        )
                    }
                }
            }
        }
    }
}