package se.refur.pin

import se.refur.pin.checksum.Luhn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

class PersonalIdentificationSweden(dateOfBirth: LocalDate, control: String) : IPersonalIdentification {

    init {
        if (!SWEDISH_CONTROL_PATTERN.matcher(control).matches()) {
            throw Exception("Invalid control format")
        }
        if (!isValid(dateOfBirth, control)) {
            throw Exception("Invalid control character")
        }
    }

    override val weekdayOfBirth: DayOfWeek =
        dateOfBirth.dayOfWeek

    override val legalGender: LegalGender =
        LegalGender.evenForFemale(control[2])

    override val shortFormat: String =
        "${dateOfBirth.toShortString()}${getSeparator(ageAtDate())}${control}"

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
            PersonalIdentificationSweden(birthDate, control)

        /**
         * Generate a random personal identification
         */
        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            val rnd: String = generateRandom(999).toString().padStart(3, '0')
            val checksum: Int = Luhn.getChecksumAsInt("${dateOfBirth.toShortString()}$rnd")
            return PersonalIdentificationSweden(dateOfBirth, "$rnd$checksum")
        }
    }
}


private fun LocalDate.toShortString(): String =
    this.format(DateTimeFormatter.ofPattern("yy-MM-dd"))
        .replace("-", "")

private fun LocalDate.toLongFormat(): String =
    this.format(DateTimeFormatter.ISO_DATE)
        .replace("-", "")