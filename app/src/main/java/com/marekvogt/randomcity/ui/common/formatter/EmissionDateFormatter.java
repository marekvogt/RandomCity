package com.marekvogt.randomcity.ui.common.formatter;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class EmissionDateFormatter implements DateFormatter {

    private static final String EMISSION_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(EMISSION_DATE_TIME_FORMAT);

    @Override
    @NotNull
    public String format(@Nonnull LocalDateTime localeDateTime) {
        return localeDateTime.format(dateTimeFormatter);
    }
}