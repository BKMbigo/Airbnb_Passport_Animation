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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
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
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.BottomSheet
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.BottomSheetState
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.BottomSheetTransitionState
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.HouseList
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.SearchField
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportBook
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportDefaults
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    listings: List<Listing> = com.github.bkmbigo.airbnbpassportanimation.listings
) {
    val coroutineScope = rememberCoroutineScope()

    var bottomSheetState by remember { mutableStateOf<BottomSheetState?>(null) }

    val sheetAnimationValue by animateFloatAsState(
        targetValue = when (bottomSheetState?.currentTransition) {
            BottomSheetTransitionState.OPENING -> 1f
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = when (bottomSheetState?.currentTransition) {
                BottomSheetTransitionState.OPENING -> 2000
                else -> 1000
            }
        ),
        label = "",
        finishedListener = { finalValue ->
            if(finalValue == 0f) {
                bottomSheetState = null
            }
        }
    )

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
            scrollState.value  /*Placed to update LookAhead Layout in case of change in scroll*/

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
                    HouseList(
                        listings = listings,
                        passport = { index ->
                            if (
                                !(bottomSheetState != null &&
                                        bottomSheetState?.listingIndex == index &&
                                        bottomSheetState?.currentTransition == BottomSheetTransitionState.OPENING)
                            ) {
                                Box(
                                    modifier = Modifier
                                ) {
                                    listingItems[index](
                                        PassportParameters(
                                            isOpen = false
                                        ),
                                        {
                                            bottomSheetState = BottomSheetState(
                                                listingIndex = index,
                                                listing = listings[index]
                                            )
                                        },
                                        Modifier
                                            .align(Alignment.BottomStart)
                                    )
                                }
                            }
                        },
                        closingBottomSheet = {
                            bottomSheetState?.let { sheetState ->
                                if (
                                    sheetState.currentTransition == BottomSheetTransitionState.CLOSING
                                ) {
                                    BottomSheet(
                                        sheetState = sheetState,
                                        animationValue = sheetAnimationValue,
                                        landlordPassport = { /* no-op */ },
                                        onDismissRequest = {
                                            bottomSheetState =
                                                sheetState.copy(
                                                    currentTransition = BottomSheetTransitionState.CLOSING
                                                )
                                        }
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, true),
                        scrollState = scrollState
                    )
                }
            }

            bottomSheetState?.let { sheetState ->
                if (
                    sheetState.currentTransition == BottomSheetTransitionState.OPENING
                ) {
                    BottomSheet(
                        sheetState = sheetState,
                        animationValue = sheetAnimationValue,
                        landlordPassport = {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            ) {
                                listingItems[sheetState.listingIndex](
                                    PassportParameters(
                                        isOpen = true
                                    ),
                                    {
                                        coroutineScope.launch {
                                            bottomSheetState =
                                                sheetState.copy(
                                                    currentTransition = BottomSheetTransitionState.CLOSING
                                                )
                                        }
                                    },
                                    Modifier
                                        .align(Alignment.Center)
                                )
                            }
                        },
                        onDismissRequest = {
                            bottomSheetState =
                                sheetState.copy(
                                    currentTransition = BottomSheetTransitionState.CLOSING
                                )
                        }
                    )
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
    val isOpen: Boolean
)