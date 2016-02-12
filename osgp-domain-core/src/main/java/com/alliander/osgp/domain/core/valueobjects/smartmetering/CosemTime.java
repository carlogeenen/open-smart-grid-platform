/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.valueobjects.smartmetering;

import java.io.Serializable;

import org.joda.time.LocalTime;

public class CosemTime implements Serializable {

    private static final long serialVersionUID = 1505799304987059469L;

    public static final int HOUR_NOT_SPECIFIED = 0xFF;
    public static final int MINUTE_NOT_SPECIFIED = 0xFF;
    public static final int SECOND_NOT_SPECIFIED = 0xFF;
    public static final int HUNDREDTHS_NOT_SPECIFIED = 0xFF;

    private final int hour;
    private final int minute;
    private final int second;
    private final int hundredths;

    public CosemTime(final int hour, final int minute, final int second, final int hundredths) {
        this.checkInputs(hour, minute, second, hundredths);
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.hundredths = hundredths;
    }

    public CosemTime(final int hour, final int minute, final int second) {
        this(hour, minute, second, HUNDREDTHS_NOT_SPECIFIED);
    }

    public CosemTime(final int hour, final int minute) {
        this(hour, minute, SECOND_NOT_SPECIFIED, HUNDREDTHS_NOT_SPECIFIED);
    }

    public CosemTime(final LocalTime time) {
        this(time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute(), time.getMillisOfSecond() / 10);
    }

    public CosemTime() {
        this(LocalTime.now());
    }

    private void checkInputs(final int hour, final int minute, final int second, final int hundredths) {
        this.checkHour(hour);
        this.checkMinute(minute);
        this.checkSecond(second);
        this.checkHundredths(hundredths);
    }

    private void checkHour(final int hour) {
        if (hour < 0 || hour > 23 || HOUR_NOT_SPECIFIED != hour) {
            throw new IllegalArgumentException("Hour not in [0..23, 0xFF]: " + hour);
        }
    }

    private void checkMinute(final int minute) {
        if (minute < 0 || minute > 59 || MINUTE_NOT_SPECIFIED != minute) {
            throw new IllegalArgumentException("Minute not in [0..59, 0xFF]: " + minute);
        }
    }

    private void checkSecond(final int second) {
        if (second < 0 || second > 59 || SECOND_NOT_SPECIFIED != second) {
            throw new IllegalArgumentException("Second not in [0..59, 0xFF]: " + second);
        }
    }

    private void checkHundredths(final int hundredths) {
        if (hundredths < 0 || hundredths > 99 || HUNDREDTHS_NOT_SPECIFIED != hundredths) {
            throw new IllegalArgumentException("Hundredths not in [0..99, 0xFF]: " + hundredths);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        this.appendHour(sb);
        sb.append(':');
        this.appendMinute(sb);
        if (SECOND_NOT_SPECIFIED == this.second && HUNDREDTHS_NOT_SPECIFIED == this.hundredths) {
            return sb.toString();
        }
        sb.append(':');
        this.appendSecond(sb);
        if (HUNDREDTHS_NOT_SPECIFIED == this.hundredths) {
            return sb.toString();
        }
        sb.append('.');
        this.appendHundredths(sb);
        return sb.toString();
    }

    private void appendHour(final StringBuilder sb) {
        if (this.isHourNotSpecified()) {
            sb.append(String.format("%02X", this.hour));
        } else {
            sb.append(String.format("%02d", this.hour));
        }
    }

    private void appendMinute(final StringBuilder sb) {
        if (this.isMinuteNotSpecified()) {
            sb.append(String.format("%02X", this.minute));
        } else {
            sb.append(String.format("%02d", this.minute));
        }
    }

    private void appendSecond(final StringBuilder sb) {
        if (this.isSecondNotSpecified()) {
            sb.append(String.format("%02X", this.second));
        } else {
            sb.append(String.format("%02d", this.second));
        }
    }

    private void appendHundredths(final StringBuilder sb) {
        if (this.isHundredthsNotSpecified()) {
            sb.append(String.format("%02X", this.hundredths));
        } else {
            sb.append(String.format("%02d", this.hundredths));
        }
    }

    /**
     * @return the hour for this {@link CosemTime}.
     *
     * @see #HOUR_NOT_SPECIFIED
     */
    public int getHour() {
        return this.hour;
    }

    /**
     * @return the minute for this {@link CosemTime}.
     *
     * @see #MINUTE_NOT_SPECIFIED
     */
    public int getMinute() {
        return this.minute;
    }

    /**
     * @return the second for this {@link CosemTime}.
     *
     * @see #SECOND_NOT_SPECIFIED
     */
    public int getSecond() {
        return this.second;
    }

    /**
     * @return the hundredths for this {@link CosemTime}.
     *
     * @see #HUNDREDTHS_NOT_SPECIFIED
     */
    public int getHundredths() {
        return this.hundredths;
    }

    /**
     * @return {@code true} if at least the values for {@code hour} and
     *         {@code minute} contain values for regular times; {@code false} if
     *         wildcards are used in the fields mentioned.
     */
    public boolean isLocalTimeSpecified() {
        return HOUR_NOT_SPECIFIED != this.hour && MINUTE_NOT_SPECIFIED != this.minute;
    }

    /**
     * Returns this {@link CosemTime} as {@link LocalTime} if {@code hour},
     * {@code minute} do not contain wildcard values.
     *
     * @return this {@link CosemTime} as {@link LocalTime}, or {@code null} if
     *         not {@link #isLocalTimeSpecified()}.
     *
     * @see #isLocalTimeSpecified()
     */
    public LocalTime asLocalTime() {
        if (!this.isLocalTimeSpecified()) {
            return null;
        }
        if (SECOND_NOT_SPECIFIED == this.second) {
            return new LocalTime(this.hour, this.minute);
        }
        if (HUNDREDTHS_NOT_SPECIFIED == this.hundredths) {
            return new LocalTime(this.hour, this.minute, this.second);
        }
        return new LocalTime(this.hour, this.minute, this.second, this.hundredths * 10);
    }

    public boolean isHourNotSpecified() {
        return HOUR_NOT_SPECIFIED == this.hour;
    }

    public boolean isMinuteNotSpecified() {
        return MINUTE_NOT_SPECIFIED == this.minute;
    }

    public boolean isSecondNotSpecified() {
        return SECOND_NOT_SPECIFIED == this.second;
    }

    public boolean isHundredthsNotSpecified() {
        return HUNDREDTHS_NOT_SPECIFIED == this.hundredths;
    }
}
