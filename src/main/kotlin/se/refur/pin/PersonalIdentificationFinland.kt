package se.refur.pin

import se.refur.pin.checksum.Modulo31
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

/**
 * The purpose of this
 */
class PersonalIdentificationFinland(dateOfBirth: LocalDate, control: String) : IPersonalIdentification {

    init {
        if (!FINNISH_CONTROL_PATTERN.matcher(control).matches()) {
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
        "${dateOfBirth.toShortString()}${getSeparator(dateOfBirth)}$control"

    override val longFormat: String = ""

    companion object : ICreate {
        private val FINNISH_CONTROL_PATTERN = Pattern.compile("^\\d{3}[0-9A-Y]$")

        private fun getSeparator(localDate: LocalDate): String = when {
            (localDate.year / 100) % 2 == 0 -> "+"
            else -> "-"
        }

        override fun isValid(birthDate: LocalDate, control: String): Boolean =
            Modulo31.isValid("${birthDate.toShortString()}$control")

        override fun create(birthDate: LocalDate, control: String): IPersonalIdentification =
            PersonalIdentificationFinland(birthDate, control)

        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            val rnd: String = generateRandom(999).toString().padStart(3, '0')
            val checksum: Char = Modulo31.getChecksumAsChar("${dateOfBirth.toShortString()}$rnd")
            return PersonalIdentificationFinland(dateOfBirth, "$rnd$checksum")
        }
    }
}

private fun LocalDate.toShortString(): String =
    this.format(DateTimeFormatter.ofPattern("dd-MM-yy"))
        .replace("-", "")
