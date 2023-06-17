package com.github.bkmbigo.airbnbpassportanimation.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.github.bkmbigo.airbnbpassportanimation.InfoItem
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportDefaults
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: BottomSheetState,
    landlordPassport: @Composable BoxScope.() -> Unit,
    animationValue: Float,
    onDismissRequest: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OpenBottomSheetContent(
            sheetState = sheetState,
            landlordPassport = landlordPassport,
            animationValue = animationValue,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun BoxScope.OpenBottomSheetContent(
    sheetState: BottomSheetState,
    landlordPassport: @Composable BoxScope.() -> Unit,
    animationValue: Float,
    onDismissRequest: () -> Unit
) {
    if(sheetState.currentTransition == BottomSheetTransitionState.OPENING) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(animationValue * 4.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismissRequest
                )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f, true),
        )

        Surface(
            modifier = Modifier
                .height(animationValue * BottomSheetDefaults.fullHeight),
            shape = RoundedCornerShape(
                topStart = 28.dp,
                topEnd = 28.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(BottomSheetDefaults.fullHeight - BottomSheetDefaults.infoHeight)
                ) {
                    /* no-op -> It's just a placeholder */
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BottomSheetDefaults.infoHeight)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    sheetState.listing.infoItems.forEach { infoItem ->
                        BottomSheetElement(
                            infoItem = infoItem,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 2.dp
                                )
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 4.dp,
                                horizontal = 24.dp
                            ),
                        color = Color.Black.copy(alpha = 0.25f)
                    )

                    Text(
                        text = sheetState.listing.landlordDescription,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
    }


    // Displays the passport on the bottom drawer
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {


        Box(
            modifier = Modifier
                .height(BottomSheetDefaults.fullHeight - BottomSheetDefaults.infoHeight)
                .align(Alignment.CenterHorizontally)
        ) {
            //if(passportPosition == BottomSheetPassportState.ON_SHEET) {
            landlordPassport()
            //}
        }

        Box(
            modifier = Modifier.height(BottomSheetDefaults.infoHeight)
        )
    }
}


@Composable
private fun BottomSheetElement(
    infoItem: InfoItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = infoItem.icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = infoItem.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp
        )
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewBottomSheet() {

    var bottomSheetState by remember { mutableStateOf<BottomSheetState?>(null) }

    val sheetAnimationValue by animateFloatAsState(
        targetValue = when (bottomSheetState?.currentTransition) {
            BottomSheetTransitionState.OPENING -> 1f
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = when (bottomSheetState?.currentTransition) {
                BottomSheetTransitionState.OPENING -> 2000
                else -> 1500
            }
        ),
        label = "",
        finishedListener = { finalValue ->
            if(finalValue == 0f) {
                bottomSheetState = null
            }
        }
    )

    AirbnbPassportAnimationTheme {
        Scaffold {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        bottomSheetState = BottomSheetState(
                            listingIndex = 0,
                            listing = listings[0]
                        )
                    }
                ) {
                    Text(text = "Show Bottom Sheet")
                }

                bottomSheetState?.let {
                    if(it.currentTransition == BottomSheetTransitionState.OPENING) {
                        BottomSheet(
                            sheetState = it,
                            landlordPassport = { },
                            animationValue = sheetAnimationValue,
                            onDismissRequest = {
                                bottomSheetState =
                                    it.copy(currentTransition = BottomSheetTransitionState.CLOSING)
                            }
                        )
                    }
                }

                bottomSheetState?.let {
                    if(it.currentTransition == BottomSheetTransitionState.CLOSING) {
                        BottomSheet(
                            sheetState = it,
                            landlordPassport = { },
                            animationValue = sheetAnimationValue,
                            onDismissRequest = {
                                bottomSheetState =
                                    it.copy(currentTransition = BottomSheetTransitionState.CLOSING)
                            }
                        )
                    }
                }

            }
        }
    }
}

data class BottomSheetState(
    val listingIndex: Int,
    val listing: Listing,
    val currentTransition: BottomSheetTransitionState = BottomSheetTransitionState.OPENING
)

enum class BottomSheetTransitionState {
    OPENING,
    CLOSING
}

private object BottomSheetDefaults {
    val infoHeight = 300.dp
    val passportVerticalPadding = 8.dp
    val fullHeight =
        infoHeight + PassportDefaults.FullOpenPassportSize.height + passportVerticalPadding + 26.dp
}