package com.github.bkmbigo.airbnbpassportanimation.presentation.components.book

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.R
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@Composable
fun OpenPage(
    listing: Listing,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackCover(
            listing = listing,
            modifier = Modifier
                .weight(1f, true)
                .fillMaxHeight()
        )

        StatsPage(
            listing = listing,
            modifier = Modifier
                .weight(1f, true)
                .fillMaxHeight()
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewOpenPage() {
    AirbnbPassportAnimationTheme {
        Scaffold {
            OpenPage(
                listing = listings[0],
                modifier = Modifier.height(160.dp)
            )
        }
    }
}