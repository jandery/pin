package se.refur.pin

import java.time.DayOfWeek

/**
 * The purpose of this interface is data for a personal identification
 *
 * @property legalGender
 * @property ageInYears
 * @property weekdayOfBirth
 * @property shortFormat
 * @property longFormat
 */
interface IPersonalIdentification {
    val legalGender: LegalGender
    val ageInYears: Int
    val weekdayOfBirth: DayOfWeek
    val shortFormat: String
    val longFormat: String
}
