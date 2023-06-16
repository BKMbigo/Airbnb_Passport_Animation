package com.github.bkmbigo.airbnbpassportanimation.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.HouseList
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.SearchField
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportBook
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportDefaults
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    listings: List<Listing> = com.github.bkmbigo.airbnbpassportanimation.listings
) {
    val coroutineScope = rememberCoroutineScope()

    var currentListing by remember { mutableStateOf<Int?>(null) } // Indicates whether a listing is currently open
    var itemsInAnimation by remember { mutableStateOf<PersistentList<Int>>(persistentListOf()) }


    // TODO: Find a better implementation
    val listingItems = remember(listings) {
        listings.map { listing ->
            movableContentWithReceiverOf<LookaheadLayoutScope, PassportParameters, () -> Unit, Modifier> { parameters, onClick, modifier ->

                val bookAnimationValue by animateFloatAsState(
                    targetValue = if (parameters.isOpen) 1f else 0f,
                    animationSpec = tween(
                        durationMillis = 3000,
                        easing = FastOutSlowInEasing
                    ),
                    label = "Passport Animation"
                )

                Box(
                    modifier = modifier
                        .size(PassportDefaults.FullOpenPassportSize)
                        .animatePassportPlacement(this)
                ) {
                    PassportBook(
                        listing = listing,
                        onClick = onClick,
                        bookAnimationValue = bookAnimationValue,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    LookaheadLayout(
        modifier = Modifier.fillMaxSize(),
        content = {
            scrollState.value

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SearchField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 2.dp,
                                horizontal = 4.dp
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, true)
                    ) {
                        HouseList(
                            listings = listings,
                            passport = { index ->
                                if (currentListing != index) {
                                    Box(
                                        modifier = Modifier
                                    ) {
                                        listingItems[index](
                                            PassportParameters(
                                                isOpen = false,
                                                isAnimating = itemsInAnimation.contains(index)
                                            ),
                                            {
                                                currentListing = index
                                                itemsInAnimation =
                                                    itemsInAnimation.add(index)
                                            },
                                            Modifier
                                                .align(Alignment.BottomStart)
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize(),
                            scrollState = scrollState
                        )

                        currentListing?.let { currentListingIndex ->
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            ) {
                                listingItems[currentListingIndex](
                                    PassportParameters(
                                        isOpen = true,
                                        isAnimating = true
                                    ),
                                    {
                                        coroutineScope.launch {
                                            currentListing = null
                                            delay(3000)
                                            itemsInAnimation =
                                                itemsInAnimation.remove(currentListingIndex)
                                        }
                                    },
                                    Modifier
                                        .align(Alignment.Center)
                                )
                            }
                        }
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

data class PassportParameters(
    val isOpen: Boolean,
    val isAnimating: Boolean
)