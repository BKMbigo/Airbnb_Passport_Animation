package com.github.bkmbigo.airbnbpassportanimation.presentation.components.book

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

object PassportDefaults {
    val PassportMargin = 16.dp
    val PassportCornerSize = 20.dp

    // Closed Passport Defaults
    val ClosedPassportPageSize = DpSize(width = 90.dp, height = 120.dp)
    val ClosedPassportSize = DpSize(width = ClosedPassportPageSize.width + PassportMargin, ClosedPassportPageSize.height)

    // Open Passport Defaults
    val OpenPassportPageSize = DpSize(width = 120.dp, height = 160.dp)
    val OpenPassportSize = DpSize(width = OpenPassportPageSize.width + PassportMargin, OpenPassportPageSize.height)
    val FullOpenPassportSize = DpSize(width = (2 * OpenPassportPageSize.width) + PassportMargin, OpenPassportPageSize.height)

}