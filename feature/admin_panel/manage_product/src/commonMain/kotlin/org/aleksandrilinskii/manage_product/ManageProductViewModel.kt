package org.aleksandrilinskii.manage_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrilinskii.nutrisport.shared.domain.Product
import com.aleksandrilinskii.nutrisport.shared.domain.ProductCategory
import com.aleksandrilinskii.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.launch
import org.aleksandrilinskii.data.domain.AdminRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ManageProductState(
    val id: String = Uuid.random().toHexString(),
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "link_to_image",
    val category: ProductCategory = ProductCategory.PROTEINS,
    val flavors: List<String>? = null,
    val weight: Int? = null,
    val price: String = ""
)

class ManageProductViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {
    var screenState by mutableStateOf(ManageProductState())
        private set
    var uploadingImage: RequestState<Unit> by mutableStateOf(RequestState.Idle)
        private set

    val isFormValid: Boolean
        get() = screenState.title.isNotBlank() &&
                screenState.description.isNotBlank() &&
                screenState.thumbnail.isNotBlank() &&
                screenState.price.isNotBlank() &&
                (screenState.weight == null || screenState.weight!! > 0)

    fun updateTitle(newTitle: String) {
        screenState = screenState.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        screenState = screenState.copy(description = newDescription)
    }

    fun updateThumbnail(newThumbnail: String) {
        screenState = screenState.copy(thumbnail = newThumbnail)
    }

    fun updateImageLoaderState(newState: RequestState<Unit>) {
        uploadingImage = newState
    }

    fun updateCategory(newCategory: ProductCategory) {
        screenState = screenState.copy(category = newCategory)
    }

    fun updateFlavors(newFlavors: String) {
        val flavors = newFlavors.split(",")
            .map { flavor -> flavor.trim() }
            .filter { flavor -> flavor.isNotBlank() }
            .ifEmpty { null }
        screenState = screenState.copy(flavors = flavors)
    }

    fun updateWeight(newWeight: String?) {
        screenState = screenState.copy(weight = newWeight?.toIntOrNull())
    }

    fun updatePrice(newPrice: String) {
        screenState = screenState.copy(price = newPrice)
    }

    fun createNewProduct(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            adminRepository.createNewProduct(
                product = Product(
                    id = screenState.id,
                    title = screenState.title,
                    description = screenState.description,
                    thumbnail = screenState.thumbnail,
                    category = screenState.category.name,
                    flavors = screenState.flavors,
                    weight = screenState.weight,
                    price = screenState.price.toDouble()
                ),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun uploadImage(
        file: File?,
        onSuccess: () -> Unit
    ) {
        if (file == null) {
            updateImageLoaderState(RequestState.Error("No image selected"))
        } else {
            updateImageLoaderState(RequestState.Loading)
            viewModelScope.launch {
                try {
                    val downloadUrl = adminRepository.uploadImageToStorage(file = file)

                    if (downloadUrl?.isNotBlank() == true) {
                        updateThumbnail(downloadUrl)
                    } else {
                        throw Exception("Failed to get download URL")
                    }

                    onSuccess()
                    updateImageLoaderState(RequestState.Success(Unit))
                } catch (e: Exception) {
                    updateImageLoaderState(RequestState.Error(e.message ?: "Unknown error"))
                }
            }

        }
    }
}