package org.aleksandrilinskii.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.component.ProfileForm

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .background(Surface)
            .systemBarsPadding()
    ) {
        ProfileForm(
            modifier = Modifier.fillMaxSize(),
            firstName = "asdsgfhgfgj",
            onFirstNameChange = {},
            lastName = "sdfgdg",
            onLastNameChange = {},
            email = "sdfghjkj",
            city = "sdgfhghjkj",
            onCityChange = {},
            postalCode = null,
            onPostalCodeChange = {},
            address = "sgfhdgfhgjh",
            onAddressChange = {},
            phone = null,
            onPhoneChange = {}
        )
    }
}