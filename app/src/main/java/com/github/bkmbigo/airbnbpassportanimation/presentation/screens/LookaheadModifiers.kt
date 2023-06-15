package com.github.bkmbigo.airbnbpassportanimation.presentation.screens

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutCoordinates
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePassportPlacement(lookaheadLayoutScope: LookaheadLayoutScope) = composed {
    var offsetAnimation: Animatable<IntOffset, AnimationVector2D>? by remember {
        mutableStateOf(
            null
        )
    }
    var placementOffset: IntOffset by remember { mutableStateOf(IntOffset.Zero) }
    var targetOffset: IntOffset? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(Unit) {
        snapshotFlow {
            targetOffset
        }.collect { target ->
            Log.i("Look ahead layout", "animatePassportPlacement: target value collected is: $target")
            if (target != null && target != offsetAnimation?.targetValue) {
                offsetAnimation?.run {
                    launch {
                        animateTo(
                            target,
                            animationSpec = tween(3000)
                    ) }
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
                    // offsetAnimation will animate the target position whenever it changes.
                    // In order to place the child at the animated position, we need to offset
                    // the child based on the target and current position in LookaheadLayout.
                    val (x, y) = offsetAnimation?.run {
                        Log.i("Look Ahead Layout", "animatePassportPlacement: offset Animation current value is $value")

                        value - placementOffset
                    }
                    // If offsetAnimation has not been set up yet (i.e. in the first frame),
                    // skip the animation
                        ?: (targetOffset!! - placementOffset)

                    Log.i("Look Ahead Layout", "animatePassportPlacement: x and y values are: x is $x, while y is $y")

                    placeable.place(x, y)
                }
            }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePlacementInScope(lookaheadScope: LookaheadLayoutScope) = composed {
    var offsetAnimation: Animatable<IntOffset, AnimationVector2D>? by remember {
        mutableStateOf(
            null
        )
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
                    launch { animateTo(target) }
                } ?: Animatable(target, IntOffset.VectorConverter).let {
                    offsetAnimation = it
                }
            }
        }
    }
    with(lookaheadScope) {
        this@composed
            .onPlaced { lookaheadScopeCoordinates, layoutCoordinates ->
                // This block of code has the LookaheadCoordinates of the LookaheadLayout
                // as the receiver. The parameter coordinates is the coordinates of this
                // modifier.
                // localLookaheadPositionOf returns the *target* position of this
                // modifier in the LookaheadLayout's local coordinates.
                targetOffset = lookaheadScopeCoordinates.localLookaheadPositionOf(
                    layoutCoordinates
                ).round()
                // localPositionOf returns the *current* position of this
                // modifier in the LookaheadLayout's local coordinates.
                placementOffset = lookaheadScopeCoordinates.localPositionOf(
                    layoutCoordinates, Offset.Zero
                ).round()
            }
            // The measure logic in `intermediateLayout` is skipped in the lookahead pass, as
            // intermediateLayout is expected to produce intermediate stages of a layout
            // transform. When the measure block is invoked after lookahead pass, the lookahead
            // size of the child will be accessible as a parameter to the measure block.
            .intermediateLayout { measurable, constraints, _ ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    // offsetAnimation will animate the target position whenever it changes.
                    // In order to place the child at the animated position, we need to offset
                    // the child based on the target and current position in LookaheadLayout.
                    val (x, y) = offsetAnimation?.run { value - placementOffset }
                    // If offsetAnimation has not been set up yet (i.e. in the first frame),
                    // skip the animation
                        ?: (targetOffset!! - placementOffset)
                    placeable.place(x, y)
                }
            }
    }
}
