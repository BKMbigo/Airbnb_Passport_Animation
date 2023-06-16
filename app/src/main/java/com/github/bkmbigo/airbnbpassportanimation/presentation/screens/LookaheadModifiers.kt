package com.github.bkmbigo.airbnbpassportanimation.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePassportPlacement(
    lookaheadLayoutScope: LookaheadLayoutScope
) = composed {
    var offsetAnimation: Animatable<IntOffset, AnimationVector2D>? by remember {
        mutableStateOf(null)
    }
    var placementOffset: IntOffset by remember { mutableStateOf(IntOffset.Zero) }
    var targetOffset: IntOffset? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(Unit) {
        snapshotFlow {
            targetOffset
        }.collect { target ->
            if (target != null && target != offsetAnimation?.targetValue) {
                offsetAnimation?.run {
                    launch {
                        animateTo(
                            target,
                            animationSpec = tween(3000)
                        )
                    }
                } ?: Animatable(target, IntOffset.VectorConverter).let {
                    offsetAnimation = it
                }
            }
        }
    }
    with(lookaheadLayoutScope) {
        this@composed
            .onPlaced { lookaheadScopeCoordinates, layoutCoordinates ->
                // This block of code has the LookaheadCoordinates of the LookaheadLayout
                // as the receiver. The parameter coordinates is the coordinates of this
                // modifier.
                // localLookaheadPositionOf returns the *target* position of this
                // modifier in the LookaheadLayout's local coordinates.

                targetOffset = lookaheadScopeCoordinates
                    .localLookaheadPositionOf(
                        layoutCoordinates
                    )
                    .round()


//                Log.i("Look ahead layout", "animatePassportPlacement: new target value is: $targetOffset")

                // localPositionOf returns the *current* position of this
                // modifier in the LookaheadLayout's local coordinates.

                placementOffset = lookaheadScopeCoordinates
                    .localPositionOf(
                        layoutCoordinates, androidx.compose.ui.geometry.Offset.Zero
                    )
                    .round()

//                Log.i("Look ahead layout", "animatePassportPlacement: new local value is: $placementOffset")

            }
            // The measure logic in `intermediateLayout` is skipped in the lookahead pass, as
            // intermediateLayout is expected to produce intermediate stages of a layout
            // transform. When the measure block is invoked after lookahead pass, the lookahead
            // size of the child will be accessible as a parameter to the measure block.
            .intermediateLayout { measurable, constraints, _ ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {

                    val (x, y) =
                        offsetAnimation?.run {
//                        Log.i("Look Ahead Layout", "animatePassportPlacement: offset Animation current value is $value")

                            // Only animate if the value of x is different
                            if (value.x != placementOffset.x) {
                                value - placementOffset
                            } else {
                                targetOffset?.let { it - placementOffset } ?: IntOffset(0, 0)
                            }

                        } ?: targetOffset?.let { it - placementOffset } ?: IntOffset(0, 0)


//                    Log.i("Look Ahead Layout", "animatePassportPlacement: x and y values are: x is $x, while y is $y")

                    placeable.place(x, y)

                }
            }
    }
}