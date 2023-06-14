package com.github.bkmbigo.airbnbpassportanimation.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.github.bkmbigo.airbnbpassportanimation.InfoItem
import com.github.bkmbigo.airbnbpassportanimation.Listing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    listing: Listing,
    landlordPassport: @Composable ColumnScope.() -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    isFocused: Boolean = true,
) {
    Popup(
        alignment = Alignment.BottomCenter,
        properties = PopupProperties(
            focusable = true
        ),
        onDismissRequest = onDismissRequest,
    ) {
        val scrimColor = MaterialTheme.colorScheme.scrim

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(onDismissRequest) {
                        detectTapGestures {
                            onDismissRequest()
                        }
                    }
                    .clearAndSetSemantics { }
            ) {
                drawRect(color = scrimColor.copy(alpha = 0.32f), alpha = if (isFocused) 1f else 0f)
            }

            Surface(
                modifier = modifier
                    .widthIn(max = 640.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter), // TODO: Add Drag Gestures
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp
                ),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        BottomSheetDefaults.DragHandle()
                    }

                    landlordPassport()

                    Spacer(modifier = Modifier.height(4.dp))

                    listing.infoItems.forEach { infoItem ->
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
                        text = listing.landlordDescription,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
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
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = infoItem.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}