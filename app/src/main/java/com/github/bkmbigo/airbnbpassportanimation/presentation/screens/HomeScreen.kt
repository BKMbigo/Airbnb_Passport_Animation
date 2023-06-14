package com.github.bkmbigo.airbnbpassportanimation.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.BottomSheet
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.HouseItem
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.SearchField
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportBook
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    listings: List<Listing> = com.github.bkmbigo.airbnbpassportanimation.listings
) {
    var currentListing by remember { mutableStateOf<Pair<Listing, @Composable (onClick: () -> Unit) -> Unit>?>(null) } // Indicates whether a listing is currently open

    val animationValue by animateFloatAsState(
        targetValue = if (currentListing != null) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        label = "Current Animation"
    )

    // TODO: Find a better implementation
    val listingItems = listings.associateWith<Listing, @Composable (onClick: () -> Unit) -> Unit> {
        @Composable { onClick ->
            PassportBook(
                listing = it,
                onClick = onClick,
                bookAnimationValue = if (currentListing?.first == it) {
                    animationValue
                } else 0f,
                modifier = Modifier.height(120.dp)
            )
        }
    }


    LookaheadLayout(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                )
                // Lazy Column cannot be used
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true)
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    listingItems.forEach { listing ->
                        HouseItem(
                            listing = listing.key,
                            landlordPassport = {
                                if (currentListing?.first != listing.key) {
                                    listing.value {
                                        currentListing = Pair(listing.key, listing.value)
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }

            currentListing?.let {
                BottomSheet(
                    listing = it.first,
                    landlordPassport = {
                        it.second(
                            onClick = {
                                currentListing = null
                            }
                        )
                    },
                    onDismissRequest = { }
                )
            }
        },
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach { it.place(0, 0) }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    AirbnbPassportAnimationTheme {
        HomeScreen()
    }
}