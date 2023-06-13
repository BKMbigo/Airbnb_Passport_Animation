package com.github.bkmbigo.airbnbpassportanimation.presentation.components.book

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme
import kotlin.math.abs

@Composable
fun PassportBook(
    listing: Listing,
    modifier: Modifier = Modifier
) {
    var isOpen by remember { mutableStateOf(true) }

    val animationValue by animateFloatAsState(
        targetValue = if (isOpen) 0f else 1f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseInOutQuart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
//            .offset(
//                x = (0.5 - abs(0.5 - animationValue)) * 120.dp
//            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isOpen = !isOpen
            }
    ) {

        Box(
            modifier = Modifier
                .offset(
                    animationValue * 120.dp
                )
        ) {
            StatsPage(
                listing = listing,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(
                            topEnd = 20.dp,
                            bottomEnd = 20.dp
                        )
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topEnd = 20.dp,
                            bottomEnd = 20.dp
                        )
                    )
            )
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationY = animationValue * -180f
//                    transformOrigin = if(isOpen) {
//                        TransformOrigin(
//                            pivotFractionX = 1.0f,
//                            pivotFractionY = 0.5f
//                        )
//                    } else {
//                        TransformOrigin(
//                            pivotFractionX = 0.0f,
//                            pivotFractionY = 0.5f
//                        )
//                    }
                }
        ) {
            if(animationValue < 0.5f) {
                FrontCover(
                    image = listing.landlordAvatar,
                    modifier = Modifier
                        .fillMaxHeight()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(
                                topEnd = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                )
            } else {
                BackCover(
                    listing = listing,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(120.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(
                                topEnd = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topEnd = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .graphicsLayer {
                            rotationY = 180f
                        }
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewPassportBook() {
    AirbnbPassportAnimationTheme {
        Scaffold {
            PassportBook(
                listing = listings[0],
                modifier = Modifier
                    .height(160.dp)
                    .padding(8.dp)
            )
        }
    }
}