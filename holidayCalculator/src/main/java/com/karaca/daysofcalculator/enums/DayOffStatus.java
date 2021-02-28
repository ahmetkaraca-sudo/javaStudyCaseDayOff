package com.karaca.daysofcalculator.enums;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Arrays;

public enum DayOffStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    DENIED("denied");

    private static final ImmutableMap<String, DayOffStatus> reverseLookup =
            Maps.uniqueIndex(Arrays.asList(DayOffStatus.values()), DayOffStatus::getValue);

    private String status;

    DayOffStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }

    public static DayOffStatus fromValue(final String value) {
        return reverseLookup.getOrDefault(value.toLowerCase(), DENIED);
    }
}
