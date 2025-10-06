package org.aleksandrilinskii.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.component.ProfileForm
import com.aleksandrilinskii.nutrisport.shared.domain.Country

@Composable
fun ProfileScreen() {
    var country by remember { mutableStateOf(Country.Serbia) }

    Box(
        modifier = Modifier
            .background(Surface)
            .systemBarsPadding()
    ) {
        ProfileForm(
            modifier = Modifier.fillMaxSize(),
            firstName = "Aleksandr",
            onFirstNameChange = {},
            lastName = "Ilinskii",
            onLastNameChange = {},
            email = "123@gmail.com",
            city = "Budapest",
            onCityChange = {},
            postalCode = null,
            onPostalCodeChange = {},
            address = "Address",
            onAddressChange = {},
            phone = null,
            onPhoneChange = {},
            country = country,
            onCountryChange = { country = it }
        )
    }
}