package org.aleksandrilinskii.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrilinskii.nutrisport.shared.domain.Country
import com.aleksandrilinskii.nutrisport.shared.domain.PhoneNumber
import com.aleksandrilinskii.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.aleksandrilinskii.data.domain.CustomerRepository

data class ProfileScreenState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val country: Country = Country.Serbia,
    val city: String? = null,
    val phone: PhoneNumber? = null,
    val postalCode: String? = null,
    val address: String? = null
)

class ProfileViewModel(
   private val customerRepository: CustomerRepository
) : ViewModel() {

    val customer = customerRepository.readCustomerFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RequestState.Loading
        )

    var screenReady: RequestState<Unit> by mutableStateOf(RequestState.Loading)
        private set
    var screenState: ProfileScreenState by mutableStateOf(ProfileScreenState())
        private set

    init {
        viewModelScope.launch {
            customer.collectLatest { data ->
                if (data.isSuccess()) {
                    val customerData = data.getSuccessData()
                    screenState = ProfileScreenState(
                        firstName = customerData.firstName,
                        lastName = customerData.lastName,
                        email = customerData.email,
                        city = customerData.city,
                        phone = customerData.phoneNumber,
                        postalCode = customerData.postalCode,
                        country = Country.entries.firstOrNull { country ->
                            country.dialCode == customerData.phoneNumber?.dialCode
                        } ?: Country.Serbia,
                        address = customerData.address
                    )
                    screenReady = RequestState.Success(Unit)
                } else if (data.isError()) {
                    screenReady = RequestState.Error(data.getErrorMessage())
                }
            }
        }
    }

    fun updateFirstName(firstName: String) {
        screenState = screenState.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        screenState = screenState.copy(lastName = lastName)
    }

    fun updateCity(city: String) {
        screenState = screenState.copy(city = city)
    }

    fun updatePhone(phone: String?) {
        val phoneNumber = if (phone.isNullOrBlank()) {
            null
        } else {
            PhoneNumber(
                dialCode = screenState.country.dialCode,
                number = phone
            )
        }
        screenState = screenState.copy(phone = phoneNumber)
    }

    fun updatePostalCode(postalCode: Int?) {
        screenState = screenState.copy(postalCode = postalCode?.toString())
    }

    fun updateAddress(address: String?) {
        screenState = screenState.copy(address = address)
    }

    fun updateCountry(country: Country) {
        val currentPhoneNumber = screenState.phone
        val newPhoneNumber = currentPhoneNumber?.let {
            PhoneNumber(
                dialCode = country.dialCode,
                number = it.number
            )
        }
        screenState = screenState.copy(
            country = country,
            phone = newPhoneNumber
        )
    }

    fun updateProfile() {
        viewModelScope.launch {
           /* customerRepository.updateCustomer(
                firstName = screenState.firstName,
                lastName = screenState.lastName,
                city = screenState.city,
                phoneNumber = screenState.phone,
                postalCode = screenState.postalCode,
                address = screenState.address
            )*/
        }
    }
}