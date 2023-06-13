package com.github.bkmbigo.airbnbpassportanimation.presentation.components.book

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.airbnbpassportanimation.R
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@Composable
internal fun FrontCover(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(
            topEnd = 20.dp,
            bottomEnd = 20.dp
        ),
        tonalElevation = 8.dp,
        shadowElevation = 2.dp
    ) {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(16.dp)
                    .shadow(4.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFBDBDBD),
                                Color(0xFFFCFCFC),
                                Color(0xFFBDBDBD),
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .width(104.dp)
                    .fillMaxHeight()
                    .background(Color.Black.copy(alpha = 0.01f))
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .shadow(16.dp, shape = CircleShape)
                        .background(Color.Red)
                ) {
//                    Image(
//                        painter = painterResource(id = image),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxSize()
//                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewFrontCover() {
    AirbnbPassportAnimationTheme {
        Scaffold {
            FrontCover(
                image = R.drawable.person_2,
                modifier = Modifier
                    .height(160.dp)
            )
        }
    }
}