package com.marekvogt.randomcity.domain.common.datetime

import java.time.LocalDateTime

interface CurrentLocalDateTimeProvider {
    fun getCurrentDateTime(): LocalDateTime
}