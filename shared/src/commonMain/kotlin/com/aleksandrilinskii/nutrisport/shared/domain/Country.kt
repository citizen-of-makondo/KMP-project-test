package com.aleksandrilinskii.nutrisport.shared.domain

import com.aleksandrilinskii.nutrisport.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class Country(
    val dialCode: Int,
    val code: String,
    val flag: DrawableResource
) {
    Serbia(381, "RS", Resources.Flag.Serbia),
    India(91, "IN", Resources.Flag.India),
    UnitedStates(1, "US", Resources.Flag.Usa),
}