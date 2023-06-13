package com.github.bkmbigo.airbnbpassportanimation.presentation.components.book

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.infoItems
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@Composable
internal fun StatsPage(
    listing: Listing,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        StatsElement(
            text = {
                Text(
                    text = listing.reviewsCount.toString()
                )
            },
            title = "Reviews"
        )

        Divider(modifier = Modifier.fillMaxWidth())

        StatsElement(
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = listing.rating.toString())
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            },
            title = "Rating"
        )

        Divider(modifier = Modifier.fillMaxWidth())

        StatsElement(
            text = {
                Text(
                    text = "1"  // TODO: Add Years Hosted
                )
            },
            title = "Years Hosting"
        )

    }
}

@Composable
private fun StatsElement(
    text: @Composable () -> Unit,
    title: String
) {
    Column {
        ProvideTextStyle(
            value = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        ) {
            text()
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 11.sp
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewStatsPage() {
    AirbnbPassportAnimationTheme {
        Scaffold {
            StatsPage(
                listing = listings[0],
                modifier = Modifier
            )
        }
    }
}