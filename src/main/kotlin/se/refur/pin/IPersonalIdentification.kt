package se.refur.pin

import java.time.DayOfWeek

/**
 * The purpose of this
 */
interface IPersonalIdentification {
    val legalGender: LegalGender
    val ageInYears: Int
    val weekdayOfBirth: DayOfWeek
    val shortFormat: String
    val longFormat: String
}


