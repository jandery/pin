package se.refur.pin

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * The purpose of this interface is data for a personal identification
 *
 * @property legalGender
 * @property weekdayOfBirth
 * @property shortFormat Country specific short format
 * @property longFormat Country specific long format
 */
interface IPersonalIdentification {
    val dateOfBirth: LocalDate
    val legalGender: LegalGender
    val weekdayOfBirth: DayOfWeek
    val ageInYears: Int
    val shortFormat: String
    val longFormat: String
}
