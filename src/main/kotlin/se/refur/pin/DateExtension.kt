package se.refur.pin

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * The purpose of this file is holding extension functions
 */


internal fun LocalDate.toShortString(): String =
    this.format(DateTimeFormatter.ofPattern("yy-MM-dd"))
        .replace("-", "")

internal fun LocalDate.toLongFormat(): String =
    this.format(DateTimeFormatter.ISO_DATE)
        .replace("-", "")