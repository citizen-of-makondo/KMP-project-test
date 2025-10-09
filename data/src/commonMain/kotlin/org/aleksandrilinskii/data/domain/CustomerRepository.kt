package org.aleksandrilinskii.data.domain

import com.aleksandrilinskii.nutrisport.shared.domain.Customer
import com.aleksandrilinskii.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun getCurrentUserId(): String?

    suspend fun signOut(): RequestState<Unit>

    fun readCustomerFlow(): Flow<RequestState<Customer>>
}