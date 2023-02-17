package se.refur.pin

import se.refur.pin.checksum.Luhn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

/**
 * The purpose of this class is get information from a Swedish personal number
 * @param dateOfBirth
 * @param control
 * @see IPersonalIdentification
 */
class PersonalIdentificationSweden(override val dateOfBirth: LocalDate, control: String) : IPersonalIdentification {

    init {
        if (!CONTROL_PATTERN.matcher(control).matches()) {
            throw Exception("Invalid control characters format")
        }
        if (!isValid(dateOfBirth, control)) {
            throw Exception("Invalid checksum")
        }
    }

    override val weekdayOfBirth: DayOfWeek =
        dateOfBirth.dayOfWeek

    override val ageInYears: Int =
        Period.between(dateOfBirth, LocalDate.now()).years

    override val legalGender: LegalGender =
        LegalGender.evenForFemale(control[2])

    override val shortFormat: String =
        "${dateOfBirth.toShortString()}${getSeparator(ageInYears)}${control}"

    override val longFormat: String =
        "${dateOfBirth.toLongFormat()}-$control"


    companion object : ICreate {
        private val CONTROL_PATTERN = Pattern.compile("^\\d{4}$")

        // Valid format for YYYYMMDDXXXX. Must start with 19 or 20 and followed by 6+4 numeric characters
        private val SSN_PATTERN_FULL = Pattern.compile("^(19|20)[0-9]{6}[0-9]{4}$")

        // Valid format for YYYYMMDD-XXXX. Must start with 19 or 20 and followed by 6+4 numeric characters
        private val SSN_PATTERN_SEPARATED_FULL = Pattern.compile("^(19|20)[0-9]{6}(-|\\+)[0-9]{4}$")

        private fun getSeparator(ageInYears: Int): String =
            if (ageInYears < 100) "-" else "+"

        /**
         * Check if a birthdate with control characters are valid
         */
        override fun isValid(birthDate: LocalDate, control: String): Boolean =
            Luhn.isValid("${birthDate.toShortString()}$control")

        /**
         * Check if a personal identification number is valid
         */
        override fun isValid(pin: String): Boolean = when {
            SSN_PATTERN_FULL.matcher(pin).matches() -> isValid(
                birthDate = pin.toLocalDate(),
                control = pin.takeLast(4)
            )

            SSN_PATTERN_SEPARATED_FULL.matcher(pin).matches() -> isValid(
                birthDate = pin.toLocalDate(),
                control = pin.takeLast(4)
            )
            else -> false
        }

        /**
         * Create Personal identification
         */
        override fun create(birthDate: LocalDate, control: String): IPersonalIdentification =
            PersonalIdentificationSweden(birthDate, control)

        /**
         * Create personal identification from string
         */
        override fun create(pin: String): IPersonalIdentification = when {
            SSN_PATTERN_FULL.matcher(pin).matches() ->
                PersonalIdentificationSweden(pin.toLocalDate(), pin.takeLast(4))
            SSN_PATTERN_SEPARATED_FULL.matcher(pin).matches() ->
                PersonalIdentificationSweden(pin.toLocalDate(), pin.takeLast(4))
            else -> throw Exception("Invalid format")
        }

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

private fun String.toLocalDate(): LocalDate =
    LocalDate.parse(this.take(8), DateTimeFormatter.BASIC_ISO_DATE)

private fun LocalDate.toShortString(): String =
    this.format(DateTimeFormatter.ofPattern("yy-MM-dd"))
        .replace("-", "")

private fun LocalDate.toLongFormat(): String =
    this.format(DateTimeFormatter.ISO_DATE)
        .replace("-", "")