package com.marekvogt.randomcity.domain.common

import com.marekvogt.randomcity.domain.common.coroutine.Dispatchers

val testDispatchers: Dispatchers = Dispatchers(
    ioDispatcher = kotlinx.coroutines.Dispatchers.Unconfined,
    computationDispatcher = kotlinx.coroutines.Dispatchers.Unconfined,
    mainDispatcher = kotlinx.coroutines.Dispatchers.Unconfined,
)