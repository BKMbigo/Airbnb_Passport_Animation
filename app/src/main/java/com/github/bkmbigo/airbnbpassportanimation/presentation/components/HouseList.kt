package com.github.bkmbigo.airbnbpassportanimation.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportBook
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportDefaults
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@Composable
fun HouseList(
    listings: List<Listing>,
    passport: @Composable BoxScope.(index: Int) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            listings.forEachIndexed { index, listing ->
                HouseItem(
                    listing = listing,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Spacer(
                modifier = Modifier
                    .height(
                        height = HouseItemDefaults.houseImageHeight - 16.dp - PassportDefaults.FullOpenPassportSize.height + 4.dp
                    )
            )

            listings.forEachIndexed { index, listing ->
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(PassportDefaults.FullOpenPassportSize)
                    ) {
                        passport(index)
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(
                            HouseItemDefaults.itemHeight -
                                    PassportDefaults.FullOpenPassportSize.height
                        )
                )
            }

        }
    }
}

@Preview
@Composable
private fun PreviewHouseList() {
    AirbnbPassportAnimationTheme {
        val passports = listings.map<Listing, @Composable () -> Unit> { listing ->
            @Composable {
                PassportBook(
                    listing = listing,
                    onClick = { /*TODO*/ },
                    bookAnimationValue = 0f
                )
            }
        }

        HouseList(
            listings = listings,
            passport = { index ->
                passports[index]()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}