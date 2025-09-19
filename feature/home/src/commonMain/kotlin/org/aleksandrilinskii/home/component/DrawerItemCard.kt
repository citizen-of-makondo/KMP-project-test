package org.aleksandrilinskii.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.IconPrimary
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import org.aleksandrilinskii.home.domain.DrawerItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun DrawerItemCard(
    drawerItem: DrawerItem,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 99.dp))
            .clickable { onItemClick() }
            .padding(
                vertical = 12.dp,
                horizontal = 12.dp
            )
    ) {
        Icon(
            painter = painterResource(drawerItem.icon),
            contentDescription = null,
            tint = IconPrimary
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = drawerItem.title,
            color = TextPrimary,
            fontSize = FontSize.REGULAR
        )
    }
}