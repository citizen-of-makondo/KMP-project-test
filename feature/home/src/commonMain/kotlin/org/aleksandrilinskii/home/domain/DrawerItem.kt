package org.aleksandrilinskii.home.domain

import com.aleksandrilinskii.nutrisport.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class DrawerItem(
    val title: String,
    val icon: DrawableResource
) {

    PROFILE(
        title = "Profile",
        icon = Resources.Icon.Person
    ),
    BLOG(
        title = "Blog",
        icon = Resources.Icon.Book
    ),
    LOCATIONS(
        title = "Locations",
        icon = Resources.Icon.MapPin
    ),
    CONTACTS(
        title = "Contacts",
        icon = Resources.Icon.Edit
    ),
    SIGN_OUT(
        title = "Sign Out",
        icon = Resources.Icon.SignOut
    ),
    ADMIN_PANEL(
        title = "Admin Panel",
        icon = Resources.Icon.Unlock
    )
}