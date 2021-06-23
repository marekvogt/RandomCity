package com.marekvogt.randomcity.util.mock

import org.mockito.Mockito

fun <T> anyKotlinObject(): T = Mockito.any<T>()