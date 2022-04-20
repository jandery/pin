package se.refur.pin

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

/**
 * The purpose of this
 */
class PersonalIdentificationDenmark(dateOfBirth: LocalDate, control: String) : IPersonalIdentification {

    init {
        if (!DANISH_CONTROL_PATTERN.matcher(control).matches()) {
            throw Exception("Invalid control format")
        }
    }

    override val weekdayOfBirth: DayOfWeek =
        dateOfBirth.dayOfWeek

    override val legalGender: LegalGender =
        LegalGender.evenForFemale(control[3])

    override val shortFormat: String =
        "${dateOfBirth.toShortString()}-${control}"


    override val longFormat: String = ""

    companion object : ICreate {
        private val DANISH_CONTROL_PATTERN = Pattern.compile("^\\d{4}$")

        override fun isValid(birthDate: LocalDate, control: String): Boolean = false

        override fun create(birthDate: LocalDate, control: String): IPersonalIdentification =
            PersonalIdentificationDenmark(birthDate, control)

        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            return PersonalIdentificationDenmark(dateOfBirth, "")
        }
    }
}

private fun LocalDate.toShortString(): String =
    this.format(DateTimeFormatter.ofPattern("dd-MM-yy"))
        .replace("-", "")