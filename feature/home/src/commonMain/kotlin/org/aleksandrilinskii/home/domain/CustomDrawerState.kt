package org.aleksandrilinskii.home.domain

enum class CustomDrawerState {

    OPEN,
    CLOSED
}

fun CustomDrawerState.isOpened() =
    this == CustomDrawerState.OPEN

fun CustomDrawerState.isClosed() =
    this == CustomDrawerState.CLOSED

fun CustomDrawerState.opposite() =
    if (isOpened()) {
        CustomDrawerState.CLOSED
    } else CustomDrawerState.OPEN