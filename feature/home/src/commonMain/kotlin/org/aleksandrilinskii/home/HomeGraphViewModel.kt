package org.aleksandrilinskii.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.aleksandrilinskii.data.domain.CustomerRepository

class HomeGraphViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    fun signOut(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                customerRepository.signOut()
            }
            if (result.isSuccess()) {
                onSuccess()
            } else {
                onError(result.getErrorMessage())
            }
        }
    }
}