package com.github.bkmbigo.airbnbpassportanimation.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.FastOutSlowInEasing
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePassportPlacement(
    lookaheadLayoutScope: LookaheadLayoutScope
) = composed {
    var offsetAnimation: Animatable<IntOffset, AnimationVector2D>? by remember {
        mutableStateOf(null)
    }

    var previousPosition = remember<IntOffset?> { null }
    var isAnimationActive = remember { false }
    var activeAnimationJob = remember<Job?> { null }

    var placementOffset: IntOffset by remember { mutableStateOf(IntOffset.Zero) }
    var targetOffset: IntOffset? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(Unit) {
        snapshotFlow {
            targetOffset
        }.collect { target ->
            if (
                target != null &&
                target != offsetAnimation?.targetValue
            ) {
                offsetAnimation?.run {
                    if (isAnimationActive || target.x != previousPosition?.x) {
                        isAnimationActive = true
                        launch {
                            animateTo(
                                target,
                                animationSpec = tween(2000, easing = FastOutSlowInEasing)
                            )
                        }
                        activeAnimationJob?.cancel()
                        activeAnimationJob = launch {
                            delay(2000)
                            if (isActive) {
                                isAnimationActive = false
                                cancel()
                            }
                        }
                    } else {
                        snapTo(target)
                    }
                } ?: Animatable(target, IntOffset.VectorConverter).let {
                    offsetAnimation = it
                }
            }
            previousPosition = target
        }
    }
    with(lookaheadLayoutScope) {
        this@composed
            .onPlaced { lookaheadScopeCoordinates, layoutCoordinates ->

                targetOffset = lookaheadScopeCoordinates
                    .localLookaheadPositionOf(
                        layoutCoordinates
                    )
                    .round()

                placementOffset = lookaheadScopeCoordinates
                    .localPositionOf(
                        layoutCoordinates, androidx.compose.ui.geometry.Offset.Zero
                    )
                    .round()

            }

            .intermediateLayout { measurable, constraints, _ ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {

                    val (x, y) =
                        offsetAnimation?.run {

                            if (value.x != placementOffset.x) {
                                value - placementOffset
                            } else {
                                targetOffset?.let { it - placementOffset } ?: IntOffset(0, 0)
                            }

                        } ?: targetOffset?.let { it - placementOffset } ?: IntOffset(0, 0)


                    placeable.place(x, y)

                }
            }
    }
}