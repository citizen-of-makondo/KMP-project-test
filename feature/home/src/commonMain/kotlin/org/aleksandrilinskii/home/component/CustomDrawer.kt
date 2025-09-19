package org.aleksandrilinskii.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.BebasNeueFont
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import com.aleksandrilinskii.nutrisport.shared.TextSecondary
import org.aleksandrilinskii.home.domain.DrawerItem

@Composable
fun CustomDrawer(
    onProfileClick: () -> Unit,
    onContactsClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdminPanelClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
            .padding(horizontal = 12.dp)
    ) {
        Spacer(Modifier.height(50.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Nutrisport".uppercase(),
            textAlign = TextAlign.Center,
            color = TextSecondary,
            fontFamily = BebasNeueFont(),
            fontSize = FontSize.EXTRA_LARGE
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Healthy Lifestyle",
            textAlign = TextAlign.Center,
            color = TextPrimary,
            fontSize = FontSize.REGULAR
        )

        Spacer(Modifier.height(50.dp))

        DrawerItem.entries.take(5).forEach { drawerItem ->
            DrawerItemCard(
                drawerItem = drawerItem,
                onItemClick = {
                    when (drawerItem) {
                        DrawerItem.PROFILE -> onProfileClick()
                        DrawerItem.CONTACTS -> onContactsClick()
                        DrawerItem.SIGN_OUT -> onSignOutClick()
                        else -> {}
                    }
                }
            )

            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.weight(1f))

        DrawerItemCard(
            drawerItem = DrawerItem.ADMIN_PANEL,
            onItemClick = onAdminPanelClick
        )
    }
}