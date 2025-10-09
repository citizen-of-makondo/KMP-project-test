package org.aleksandrilinskii.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.BebasNeueFont
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.IconPrimary
import com.aleksandrilinskii.nutrisport.shared.Resources
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import com.aleksandrilinskii.nutrisport.shared.component.ErrorCard
import com.aleksandrilinskii.nutrisport.shared.component.LoadingCard
import com.aleksandrilinskii.nutrisport.shared.component.PrimaryButton
import com.aleksandrilinskii.nutrisport.shared.component.ProfileForm
import com.aleksandrilinskii.nutrisport.shared.util.DisplayResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val screenState = viewModel.screenState
    val screenReady = viewModel.screenReady

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My profile",
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp, bottom = 24.dp)
        ) {
            screenReady.DisplayResult(
                modifier = Modifier,
                onIdle = {},
                onLoading = {
                    LoadingCard(modifier = Modifier.fillMaxSize())
                },
                onError = { message ->
                    ErrorCard(
                        modifier = Modifier.fillMaxSize(),
                        message = message
                    )
                },
                onSuccess = { state ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        ProfileForm(
                            modifier = Modifier.weight(1f),
                            firstName = screenState.firstName,
                            onFirstNameChange = viewModel::updateFirstName,
                            lastName = screenState.lastName,
                            onLastNameChange = viewModel::updateLastName,
                            email = screenState.email,
                            city = screenState.city,
                            onCityChange = viewModel::updateCity,
                            postalCode = screenState.postalCode?.toIntOrNull(),
                            onPostalCodeChange = viewModel::updatePostalCode,
                            address = screenState.address,
                            onAddressChange = viewModel::updateAddress,
                            phone = screenState.phone?.number.orEmpty(),
                            onPhoneChange = viewModel::updatePhone,
                            country = screenState.country,
                            onCountryChange = viewModel::updateCountry
                        )

                        Spacer(Modifier.height(12.dp))

                        PrimaryButton(
                            text = "Update profile",
                            icon = Resources.Icon.Checkmark,
                            onClick = viewModel::updateProfile
                        )
                    }
                },
                transitionSpec = null,
                backgroundColor = null
            )
        }
    }
}