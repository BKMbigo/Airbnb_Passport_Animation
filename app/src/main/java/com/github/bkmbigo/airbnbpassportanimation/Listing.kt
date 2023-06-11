package com.github.bkmbigo.airbnbpassportanimation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Listing(
    val id: Int,
    @DrawableRes
    val coverUrl: Int,
    @DrawableRes
    val landlordAvatar: Int,
    val landlordName: String,
    val landlordDescription: String,
    val title: String,
    val address: String,
    val availability: String,
    val rating: Float = 0f,
    val reviewsCount: Int = 0,
    val price: Float,
    val infoItems: List<InfoItem> = emptyList()
)

data class InfoItem(
    val icon: ImageVector,
    val text: String
)
