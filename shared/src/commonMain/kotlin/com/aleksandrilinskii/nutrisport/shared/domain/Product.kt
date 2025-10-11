package com.aleksandrilinskii.nutrisport.shared.domain

import androidx.compose.ui.graphics.Color
import com.aleksandrilinskii.nutrisport.shared.CategoryBlue
import com.aleksandrilinskii.nutrisport.shared.CategoryGreen
import com.aleksandrilinskii.nutrisport.shared.CategoryPurple
import com.aleksandrilinskii.nutrisport.shared.CategoryRed
import com.aleksandrilinskii.nutrisport.shared.CategoryYellow
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val category: String,
    val flavors: List<String>? = null,
    val weight: Int? = null,
    val price: Double,
    val isPopular: Boolean = false,
    val isDiscount: Boolean = false,
    val isNew: Boolean = false
)

enum class ProductCategory(val title: String, val color: Color) {
    PROTEINS("Proteins", CategoryYellow),
    CREATINS("Creatins", CategoryBlue),
    PRE_WORKOUTS("Pre-workouts", CategoryGreen),
    GAINERS("Gainers", CategoryPurple),
    ACCESSORIES("Accessories", CategoryRed);
}