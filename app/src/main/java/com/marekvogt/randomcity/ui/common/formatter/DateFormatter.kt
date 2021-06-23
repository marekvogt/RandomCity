package com.marekvogt.randomcity.ui.common.formatter

import java.time.LocalDateTime

interface DateFormatter {

    fun format(localDateTime: LocalDateTime): String
}