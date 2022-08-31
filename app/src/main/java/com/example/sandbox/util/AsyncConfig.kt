package com.example.sandbox.util

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@PublicAPI
fun launchOnMain(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = mainScope().launch(context, start, block)

@PublicAPI
fun launchOnIO(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = ioScope().launch(context, start, block)

@PublicAPI
fun launchOnBackground(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = backgroundScope().launch(context, start, block)

@PublicAPI
suspend fun <T> main(block: suspend CoroutineScope.() -> T): T =
    withContext(AsyncConfig.mainDispatcher, block)

@PublicAPI
suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
    withContext(AsyncConfig.ioDispatcher, block)

@PublicAPI
suspend fun <T> background(block: suspend CoroutineScope.() -> T): T =
    withContext(AsyncConfig.backgroundDispatcher, block)

private fun mainScope() = CoroutineScope(SupervisorJob() + AsyncConfig.mainDispatcher)

private fun ioScope() = CoroutineScope(SupervisorJob() + AsyncConfig.ioDispatcher)

private fun backgroundScope() = CoroutineScope(SupervisorJob() + AsyncConfig.backgroundDispatcher)

object AsyncConfig {

    @Volatile
    var mainDispatcher = Dispatchers.Main

    @Volatile
    var ioDispatcher = Dispatchers.IO

    @Volatile
    var backgroundDispatcher = Dispatchers.Default

}