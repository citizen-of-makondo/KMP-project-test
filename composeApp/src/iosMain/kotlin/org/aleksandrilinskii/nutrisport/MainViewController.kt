package org.aleksandrilinskii.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import org.aleksandrilinskii.di.initializeKoinModule

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoinModule() }
) { App() }