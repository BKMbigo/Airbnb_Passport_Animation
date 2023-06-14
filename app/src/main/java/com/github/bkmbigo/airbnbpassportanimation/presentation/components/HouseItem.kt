package com.github.bkmbigo.airbnbpassportanimation.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.airbnbpassportanimation.Listing
import com.github.bkmbigo.airbnbpassportanimation.R
import com.github.bkmbigo.airbnbpassportanimation.listings
import com.github.bkmbigo.airbnbpassportanimation.presentation.components.book.PassportBook
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@Composable
fun HouseItem(
    listing: Listing,
    landlordPassport: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(10.dp))
                .shadow(2.dp, RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = listing.coverUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = {  },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp)
            ) {
                landlordPassport()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = listing.address,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = listing.rating.toString(),
                    fontSize = 13.sp
                )
            }
        }

        Text(
            text = listing.title,
            color = Color.DarkGray,
            fontSize = 16.sp
        )

        Text(
            text = listing.availability,
            color = Color.DarkGray,
            fontSize = 12.sp
        )

        Text(
            text = buildAnnotatedString {
                withStyle(
                    LocalTextStyle.current.toSpanStyle().copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("\$${listing.price}")
                }

                withStyle(
                    LocalTextStyle.current.toSpanStyle().copy(
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                ) {
                    append(" night")
                }
            }
        )
    }
}



@Preview
@Composable
private fun PreviewHouseItem() {

    var isOpen by remember { mutableStateOf(false) }

    val animationValue by animateFloatAsState(
        targetValue = if(isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        label = "rotation"
    )

    AirbnbPassportAnimationTheme {
        HouseItem(
            listing = listings[0],
            landlordPassport = {
                PassportBook(
                    listing = listings[0],
                    onClick = {
                        isOpen = !isOpen
                    },
                    bookAnimationValue = animationValue,
                    modifier = Modifier.height(120.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                )
        )
    }
}