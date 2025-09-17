package org.aleksandrilinskii.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.Surface
import org.aleksandrilinskii.home.component.BottomBar

@Composable
fun HomeGraphScreen() {
    Scaffold(
        containerColor = Surface
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.padding(12.dp)) {
                BottomBar(
                    selected = false,
                    onClick = { destination -> }
                )
            }
        }
    }
}