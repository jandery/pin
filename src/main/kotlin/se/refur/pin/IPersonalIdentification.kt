package se.refur.pin

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period

/**
 * The purpose of this interface is data for a personal identification
 *
 * @property legalGender
 * @property weekdayOfBirth
 * @property shortFormat
 * @property longFormat
 */
interface IPersonalIdentification {
    val legalGender: LegalGender
    val weekdayOfBirth: DayOfWeek
    val shortFormat: String
    val longFormat: String

    fun ageAtDate(localDate: LocalDate = LocalDate.now()): Int =
        Period.between(localDate, LocalDate.now()).years

}
