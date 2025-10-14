package org.aleksandrilinskii.data.domain

import com.aleksandrilinskii.nutrisport.shared.domain.Product

interface AdminRepository {
    fun getCurrentUserId(): String?

    suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}