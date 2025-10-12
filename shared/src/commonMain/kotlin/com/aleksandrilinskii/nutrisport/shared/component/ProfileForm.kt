package com.aleksandrilinskii.nutrisport.shared.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aleksandrilinskii.nutrisport.shared.component.dialog.CountryPickerDialog
import com.aleksandrilinskii.nutrisport.shared.domain.Country

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    city: String?,
    onCityChange: (String) -> Unit,
    postalCode: Int?,
    onPostalCodeChange: (Int?) -> Unit,
    address: String?,
    onAddressChange: (String) -> Unit,
    phone: String?,
    onPhoneChange: (String?) -> Unit,
    country: Country,
    onCountryChange: (Country) -> Unit
) {
    var showCountryPicker by remember { mutableStateOf(false) }

    AnimatedVisibility(showCountryPicker) {
        CountryPickerDialog(
            country = country,
            onDismiss = { showCountryPicker = false },
            onConfirm = { country ->
                onCountryChange(country)
                showCountryPicker = false
            }
        )
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            placeholder = "First Name",
            modifier = Modifier.padding(top = 16.dp),
            error = firstName.length !in 3..50
        )

        CustomTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            placeholder = "Last Name",
            error = lastName.length !in 3..50
        )

        CustomTextField(
            value = email,
            onValueChange = {},
            placeholder = "Email",
            enabled = false
        )

        CustomTextField(
            value = city.orEmpty(),
            onValueChange = onCityChange,
            placeholder = "City",
            error = city == null || city.length !in 3..50
        )

        CustomTextField(
            value = postalCode?.toString().orEmpty(),
            onValueChange = { onPostalCodeChange(it.toIntOrNull()) },
            placeholder = "Postal Code",
            error = postalCode == null || postalCode.toString().length !in 3..10,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        CustomTextField(
            value = address.orEmpty(),
            onValueChange = onAddressChange,
            placeholder = "Address",
            error = address?.length !in 3..100
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AlertTextField(
                icon = country.flag,
                text = "+${country.dialCode}",
                onClick = { showCountryPicker = true }
            )

            Spacer(Modifier.width(12.dp))

            CustomTextField(
                value = phone.orEmpty(),
                onValueChange = { onPhoneChange(it.ifBlank { null }) },
                placeholder = "Phone",
                error = phone != null && phone.length !in 5..30,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }
    }
}