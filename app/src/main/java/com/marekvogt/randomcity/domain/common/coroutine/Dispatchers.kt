package com.marekvogt.randomcity.domain.common.coroutine

import kotlinx.coroutines.CoroutineDispatcher

class Dispatchers(
    val ioDispatcher: CoroutineDispatcher,
    val computationDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher
)