package org.aleksandrilinskii.data

import com.aleksandrilinskii.nutrisport.shared.domain.Customer
import com.aleksandrilinskii.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
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

    override fun readCustomerFlow(): Flow<RequestState<Customer>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId == null) {
                send(RequestState.Error("User is not authenticated"))
            } else {
                val database = Firebase.firestore
                database.collection("customer")
                    .document(userId)
                    .snapshots
                    .collectLatest { document ->
                        if (document.exists) {
                            val customer = Customer(
                                id = document.id,
                                firstName = document.get("firstName"),
                                lastName = document.get("lastName"),
                                email = document.get("email"),
                                city = document.get("city"),
                                postalCode = document.get("postalCode"),
                                address = document.get("address"),
                                phoneNumber = document.get("phoneNumber"),
                                cart = document.get("cart")
                            )

                            send(RequestState.Success(customer))
                        } else {
                            send(RequestState.Error("Customer document does not exist"))
                        }
                    }
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while reading customer info: ${e.message}"))
        }
    }

    override suspend fun updateCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId == null) {
                onError("User is not authenticated")
            } else {
                val firestore = Firebase.firestore
                val customerCollection = firestore.collection("customer")

                val existingCustomer = customerCollection.document(customer.id).get()

                if (existingCustomer.exists) {
                    customerCollection
                        .document(customer.id)
                        .update(
                            "firstName" to customer.firstName,
                            "lastName" to customer.lastName,
                            "city" to customer.city,
                            "postalCode" to customer.postalCode,
                            "address" to customer.address,
                            "phoneNumber" to customer.phoneNumber
                        )
                    onSuccess()
                } else {
                    onError("Customer does not exist")
                }
            }
        } catch (e: Exception) {
            onError("Error updating customer: ${e.message}")
        }
    }
}