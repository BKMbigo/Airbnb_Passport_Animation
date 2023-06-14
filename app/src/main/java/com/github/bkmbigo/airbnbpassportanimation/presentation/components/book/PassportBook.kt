package com.github.bkmbigo.airbnbpassportanimation.presentation.components.book

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme
import kotlin.math.roundToInt

@Composable
fun PassportBook(
    listing: Listing,
    onClick: () -> Unit,
    bookAnimationValue: Float,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .width(
                width = 106.dp + ((bookAnimationValue - 0.5f).coerceAtLeast(0f) * 180.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(16.dp)
//                    .shadow(4.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFCFCFC),
                                Color(0xFFCECECE),
                                Color(0xFFFCFCFC),
                            )
                        )
                    )
            )

            StatsPage(
                listing = listing,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(90.dp)
                    .padding(start = 4.dp)
            )
        }

        Box(
            modifier = Modifier
                .offset(
                    x = (bookAnimationValue - 0.5f).coerceAtLeast(0f) * 180.dp
                )
                .graphicsLayer {
                    rotationY = bookAnimationValue * -180f
                    transformOrigin =
                        TransformOrigin(
                            pivotFractionX = 0.0f,
                            pivotFractionY = 0.5f
                        )
                }
        ) {
            if (bookAnimationValue < 0.5f) {
                FrontCover(
                    image = listing.landlordAvatar,
                    modifier = Modifier
                        .width(106.dp)
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
                        .width(90.dp)
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
                        .clickable {
                            onClick()
                        }
                )
            }
        }

        if (bookAnimationValue == 1f) {
            Row(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.width(90.dp))
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxHeight()
                        .width(16.dp)
                ) {}
            }
        }

    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewPassportBook() {

    var isOpen by remember { mutableStateOf(false) }

    val animationValue by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        label = "rotation"
    )

    AirbnbPassportAnimationTheme {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PassportBook(
                    listing = listings[0],
                    onClick = {
                        isOpen = !isOpen
                    },
                    bookAnimationValue = animationValue,
                    modifier = Modifier
                        .height(160.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}