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

    override val ageInYears: Int =
        Period.between(dateOfBirth, LocalDate.now()).years

    override val weekdayOfBirth: DayOfWeek =
        dateOfBirth.dayOfWeek

    override val legalGender: LegalGender = when {
        Character.getNumericValue(control[2]) % 2 == 0 -> LegalGender.FEMALE
        else -> LegalGender.MALE
    }

    override val shortFormat: String =
        "${dateOfBirth.toShortString()}${getSeparator(ageInYears)}${control}"

    override val longFormat: String =
        "${dateOfBirth.toLongFormat()}-$control"

    companion object : ICreate {
        private val SWEDISH_CONTROL_PATTERN = Pattern.compile("^\\d{4}$")

        private fun getSeparator(ageInYears: Int): String = when {
            ageInYears < 100 -> "-"
            else -> "+"
        }

        /**
         * Check if a birthdate with control characters are valid
         */
        override fun isValid(birthDate: LocalDate, control: String): Boolean =
            Luhn.isValid("${birthDate.toShortString()}$control")

        /**
         * Create Personal identification
         */
        override fun create(birthDate: LocalDate, control: String): IPersonalIdentification =
            SwedishPersonalIdentification(birthDate, control)

        /**
         * Generate a random personal identification
         */
        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            val rnd: String = generateRandom(999).toString().padStart(3, '0')
            val checksum: Int = Luhn.getChecksum("${dateOfBirth.toShortString()}$rnd")
            return SwedishPersonalIdentification(dateOfBirth, "$rnd$checksum")
        }
    }
}