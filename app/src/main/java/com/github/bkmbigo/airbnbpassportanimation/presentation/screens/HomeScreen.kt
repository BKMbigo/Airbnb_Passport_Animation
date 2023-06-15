package com.github.bkmbigo.airbnbpassportanimation.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.HouseItem
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.SearchField
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    listings: List<Listing> = com.github.bkmbigo.airbnbpassportanimation.listings
) {
    var currentListing by remember { mutableStateOf<Int?>(null) } // Indicates whether a listing is currently open
    val itemsInAnimation by remember { mutableStateOf<PersistentList<Int>>(persistentListOf()) }

    // TODO: Find a better implementation
    val listingItems = remember(listings) {
        listings.map {
            movableContentWithReceiverOf<LookaheadLayoutScope, Boolean, Modifier> { animate, modifier ->
                Box(
                    modifier = modifier
                        .size(106.dp, 120.dp)
                        .padding(4.dp)
                        .then(
                            if(animate) {
                                Modifier.animatePassportPlacement(this)
                            } else {
                                Modifier
                            }
                        )
                        .background(Color.Red)
                )
            }
        }
    }

    val scrollState = rememberScrollState()

    LookaheadLayout(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            scrollState.value
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SearchField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 4.dp,
                                horizontal = 4.dp
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState)
                    ) {
                        listings.forEachIndexed { index, listing ->
                            HouseItem(
                                listing = listing,
                                landlordPassport = {
                                    if (currentListing != index) {
                                        Box(
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = null,
                                                    onClick = {
                                                        currentListing = index
                                                    }
                                                )
                                        ) {
                                            listingItems[index](
                                                true,
                                                Modifier
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                currentListing?.let { currentListingIndex ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    currentListing = null
                                }
                            )
                    ) {
                        listingItems[currentListingIndex](
                            true,
                            Modifier
                        )
                    }
                }

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