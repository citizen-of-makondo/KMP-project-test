package com.aleksandrilinskii.nutrisport.shared.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<out T>(val data: T) : RequestState<T>()
    data class Error(val message: String) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error
    fun isIdle() = this is Idle

    fun getSuccessData(): T = (this as Success).data
    fun getSuccessDataOrNull(): T? = if (this is Success) this.getSuccessData() else null
    fun getErrorMessage(): String = (this as Error).message
}

@Composable
fun <T> RequestState<T>.DisplayResult(
    modifier: Modifier = Modifier,
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: (@Composable () -> Unit)? = null,
    onError: (@Composable (String) -> Unit)? = null,
    onSuccess: @Composable (T) -> Unit,
    transitionSpec: ContentTransform? = scaleIn(tween(durationMillis = 400)) +
            fadeIn(tween(durationMillis = 800))
            togetherWith scaleOut(tween(durationMillis = 400)) +
            fadeOut(tween(durationMillis = 800)),
    backgroundColor: Color? = null
) {
    AnimatedContent(
        modifier = modifier,
        targetState = this,
        transitionSpec = {
            transitionSpec ?: (EnterTransition.None togetherWith ExitTransition.None)
        },
        label = "RequestStateDisplay"
    ) { state ->
        when (state) {
            is RequestState.Idle -> onIdle?.invoke()
            is RequestState.Loading -> onLoading?.invoke()
            is RequestState.Error -> onError?.invoke(state.message)
            is RequestState.Success -> onSuccess.invoke(state.getSuccessData())
        }
    }
}