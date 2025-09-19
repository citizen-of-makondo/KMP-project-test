package org.aleksandrilinskii.data

import com.aleksandrilinskii.nutrisport.shared.domain.Customer
import com.aleksandrilinskii.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.aleksandrilinskii.data.domain.CustomerRepository

class CustomerRepositoryImpl : CustomerRepository {

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            if (user == null) {
                onError("User is not available")
                return
            }

            val customerCollection = Firebase.firestore.collection(collectionPath = "customer")
            val customer = Customer(
                id = user.uid,
                firstName = user.displayName?.split(" ")?.firstOrNull().orEmpty(),
                lastName = user.displayName?.split(" ")?.getOrNull(1).orEmpty(),
                email = user.email ?: "Unknown"
            )

            val customerExists = customerCollection.document(user.uid).get().exists
            if (!customerExists) {
                customerCollection.document(user.uid).set(customer)
            }

            onSuccess()
        } catch (e: Exception) {
            onError("Error creating customer: ${e.message}")
        }
    }

    override fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun signOut(): RequestState<Unit> {
        return try {
            Firebase.auth.signOut()
            RequestState.Success(Unit)
        } catch (e: Exception) {
            RequestState.Error("Error signing out: ${e.message}")
        }
    }
}