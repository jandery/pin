package se.refur.pin

import se.refur.pin.checksum.Luhn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.util.regex.Pattern

class SwedishPersonalIdentification(dateOfBirth: LocalDate, control: String) : IPersonalIdentification {

    init {
        if (!SWEDISH_CONTROL_PATTERN.matcher(control).matches()) {
            throw Exception("Invalid control format")
        }
        if (!Luhn.isValid("${dateOfBirth.toShortString()}$control")) {
            throw Exception("Invalid control character")
        }
    }

    override val ageInYears: Int = Period.between(dateOfBirth, LocalDate.now()).years

    override val weekdayOfBirth: DayOfWeek = dateOfBirth.dayOfWeek

    override val legalGender: LegalGender = when {
        Character.getNumericValue(control[2]) % 2 == 0 -> LegalGender.FEMALE
        else -> LegalGender.MALE
    }

    override val shortFormat: String =
        "${dateOfBirth.toShortString()}${getSeparator(ageInYears)}${control}"

    override val longFormat: String =
        "${dateOfBirth.toLongFormat()}-$control"

    companion object {
        private val SWEDISH_CONTROL_PATTERN = Pattern.compile("^\\d{4}$")

        private fun getSeparator(ageInYears: Int): String = when {
            ageInYears < 100 -> "-"
            else -> "+"
        }
    }
}