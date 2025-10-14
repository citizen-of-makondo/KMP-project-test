package org.aleksandrilinskii.manage_product

import ContentWithMessageBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.BebasNeueFont
import com.aleksandrilinskii.nutrisport.shared.BorderIdle
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.IconPrimary
import com.aleksandrilinskii.nutrisport.shared.Resources
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.SurfaceLighter
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import com.aleksandrilinskii.nutrisport.shared.component.AlertTextField
import com.aleksandrilinskii.nutrisport.shared.component.CustomTextField
import com.aleksandrilinskii.nutrisport.shared.component.PrimaryButton
import com.aleksandrilinskii.nutrisport.shared.component.dialog.CategoryDialog
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    id: String?,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<ManageProductViewModel>()
    val messageBarState = rememberMessageBarState()
    val screenState = viewModel.screenState
    val isFormValid = viewModel.isFormValid
    var showCategoryDialog by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = showCategoryDialog,
        content = {
            CategoryDialog(
                category = screenState.category,
                onDismiss = { showCategoryDialog = false },
                onSelectCategory = { category ->
                    viewModel.updateCategory(category)
                    showCategoryDialog = false
                }
            )
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (id == null) "Add Product" else "Edit Product",
                        color = TextPrimary,
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.LARGE
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        content = {
                            Icon(
                                painter = painterResource(Resources.Icon.BackArrow),
                                contentDescription = null,
                                tint = IconPrimary
                            )
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Surface,
                    scrolledContainerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        },
        containerColor = Surface
    ) { paddingValues ->
        ContentWithMessageBar(
            messageBarState = messageBarState,
            contentBackgroundColor = Surface,
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
            errorMaxLines = 2
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp, bottom = 12.dp)
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = BorderIdle,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(SurfaceLighter)
                            .clickable {

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.Plus),
                            contentDescription = null,
                            tint = IconPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    CustomTextField(
                        value = screenState.title,
                        onValueChange = viewModel::updateTitle,
                        placeholder = "Title"
                    )

                    CustomTextField(
                        modifier = Modifier.height(120.dp),
                        value = screenState.description,
                        onValueChange = viewModel::updateDescription,
                        placeholder = "Description",
                        expanded = true
                    )

                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.category.title,
                        onClick = { showCategoryDialog = true }
                    )

                    CustomTextField(
                        value = screenState.weight?.toString() ?: "",
                        onValueChange = viewModel::updateWeight,
                        placeholder = "Weight",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    CustomTextField(
                        value = screenState.flavors?.joinToString(", ") ?: "",
                        onValueChange = viewModel::updateFlavors,
                        placeholder = "Flavors (optional)"
                    )

                    CustomTextField(
                        value = screenState.price,
                        onValueChange = viewModel::updatePrice,
                        placeholder = "Price",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(44.dp))
                }

                PrimaryButton(
                    text = if (id == null) "Add Product" else "Save Changes",
                    icon = if (id == null) Resources.Icon.Plus else Resources.Icon.Checkmark,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isFormValid,
                    onClick = {
                        viewModel.createNewProduct(
                            onSuccess = { messageBarState.addSuccess("Product added successfully") },
                            onError = { errorMessage -> messageBarState.addError(errorMessage) }
                        )
                    },
                )
            }
        }
    }
}