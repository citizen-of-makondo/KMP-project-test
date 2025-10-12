package com.aleksandrilinskii.nutrisport.shared.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.Alpha
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.IconPrimary
import com.aleksandrilinskii.nutrisport.shared.Resources
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import com.aleksandrilinskii.nutrisport.shared.TextSecondary
import com.aleksandrilinskii.nutrisport.shared.domain.ProductCategory
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryDialog(
    category: ProductCategory,
    onDismiss: () -> Unit,
    onSelectCategory: (ProductCategory) -> Unit
) {
    var selectedCategory by remember(category) { mutableStateOf(category) }

    AlertDialog(
        containerColor = Surface,
        title = {
            Text(
                text = "Pick your country",
                fontSize = FontSize.EXTRA_MEDIUM
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                ProductCategory.entries.forEach { currentCategory ->
                    val animateColor = animateColorAsState(
                        targetValue = if (selectedCategory == currentCategory) {
                            selectedCategory.color
                        } else Color.Transparent,
                        label = "Background color animation"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(animateColor.value)
                            .clickable { selectedCategory = currentCategory }
                            .padding(
                                horizontal = 12.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = currentCategory.title,
                            fontSize = FontSize.REGULAR,
                            color = TextPrimary
                        )

                        Spacer(Modifier.width(12.dp))

                        AnimatedVisibility(visible = currentCategory == selectedCategory) {
                            Icon(
                                painter = painterResource(Resources.Icon.Checkmark),
                                contentDescription = null,
                                tint = IconPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onSelectCategory(selectedCategory) },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextSecondary
                )
            ) {
                Text(
                    text = "Confirm",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextPrimary.copy(alpha = Alpha.HALF)
                )
            ) {
                Text(
                    text = "Cancel",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}