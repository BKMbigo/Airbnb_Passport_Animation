package com.github.bkmbigo.airbnbpassportanimation.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.airbnbpassportanimation.ui.theme.AirbnbPassportAnimationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier
) {

    OutlinedCard(
        onClick = { /* no-op */ },
        modifier = modifier
            .shadow(
                16.dp,
                shape = RoundedCornerShape(40)
            ),
        shape = RoundedCornerShape(40),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black.copy(alpha = 0.2f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f, true),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Where to?",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = "Anywhere . Any week . Add Guests",
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Clip
                )
            }

            OutlinedIconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewSearchField() {
    AirbnbPassportAnimationTheme {
        Scaffold {
            SearchField(modifier = Modifier.fillMaxWidth().padding(all = 4.dp))
        }
    }
}