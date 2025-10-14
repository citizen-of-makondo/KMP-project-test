package org.aleksandrilinskii.data

import com.aleksandrilinskii.nutrisport.shared.domain.Product
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.withTimeout
import org.aleksandrilinskii.data.domain.AdminRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AdminRepositoryImpl : AdminRepository {
    override fun getCurrentUserId(): String? =
        Firebase.auth.currentUser?.uid

    override suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()
            if (currentUserId != null) {
                val firestore = Firebase.firestore

                val productCollection = firestore.collection("product")

                productCollection.document(product.id).set(product)
                onSuccess()
            } else {
                onError("User not authenticated")
            }
        } catch (e: Exception) {
            onError("Failed to create product: ${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun uploadImageToStorage(file: File): String? {
        return if (getCurrentUserId() != null) {
            val storage = Firebase.storage.reference
            val storageRef = storage.child("images/${Uuid.random().toHexString()}")
            try {
                withTimeout(20000) {
                    storageRef.putFile(file)
                    storageRef.getDownloadUrl()
                }
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }
}
