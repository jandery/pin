import java.lang.RuntimeException
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import java.util.regex.Pattern

/**
 * Validate a Swedish Personal Identification number (personnummer)
 */
class SwedishPersonalIdentification(private val identification: String) : PersonalIdentification {

    // Is the format valid
    override val isValid: Boolean = when (SWEDISH_SSN_PATTERN_FULL.matcher(identification).matches()) {
        true -> isChecksumValid(identification) && isDateValid(identification)
        else -> false
    }

    // Does the identification belong to a man
    override val isMale: Boolean = isValid && Character.getNumericValue(identification[10]) % 2 != 0

    // Does the identification belong to a woman
    override val isFemale: Boolean = isValid && Character.getNumericValue(identification[10]) % 2 == 0

    // Format number
    override fun getFormatted(): String =
            if (isValid) "${identification.substring(0, 8)}${getSeparator(identification)}${identification.substring( 8)}"
            else throw RuntimeException("Invalid identification")


    companion object {
        // Valid format for YYYYMMDDXXXX. Must start with 19 or 20 and followed by 6+4 numeric characters
        private val SWEDISH_SSN_PATTERN_FULL = Pattern.compile("^(19|20)[0-9]{6}[0-9]{4}$")

        // Valid format for YYMMDDXXXX. Contains 6+4 numeric characters
        private val SWEDISH_SSN_PATTERN_SHORT = Pattern.compile("^[0-9]{6}[0-9]{4}$")

        private fun generateRandom(maxValue: Int): Int =
                ThreadLocalRandom.current().nextInt(0, maxValue)

        private fun getSeparator(ssn: String): String =
                if (isOneHundredPlus(ssn)) "+" else "-"

        private fun isOneHundredPlus(ssn: String): Boolean =
                LocalDate.now(ZoneId.of("UTC")).year - ssn.substring(0, 4).toInt() >= 100

        /**
         * Return century for short SSN
         * example:
         *  in: 7101015555 => out: 19
         *  in: 0101016666 => out: 20
         *  in: 1701016666 => out: 20
         *  @param ssn
         *  @return century part of year as string
         */
        private fun getCentury(ssn: String): String =
                if (ssn.substring(0, 2).toInt() > LocalDate.now(ZoneId.of("UTC")).year % 100) "19" else "20"

        /**
         * Calculate the checksum according to
         * https://en.wikipedia.org/wiki/Luhn_algorithm
         * @param ssn string to validate
         * @return the correct last integer in a Swedish SSN
         */
        private fun getChecksumValue(ssn: String): Int {
            val checksum: Int = ssn.subSequence(2, 11)
                    .mapIndexed { index, c ->
                        val multiplier: Int = if (index % 2 == 0) 2 else 1
                        val value: Int = Character.getNumericValue(c)
                        if (value < 9) (value * multiplier) % 9 else 9
                    }
                    .sum()

            return (10 - checksum % 10) % 10
        }

        private fun isChecksumValid(ssn: String): Boolean =
                getChecksumValue(ssn) == Integer.parseInt(ssn.substring(11, 12))

        private fun isDateValid(ssn: String): Boolean =
                kotlin.runCatching {
                    LocalDate.parse(ssn.subSequence(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"))
                }.isSuccess


        fun create(identification: String): PersonalIdentification =
                identification.replace("-", "").replace("+", "").let {
                    when {
                        SWEDISH_SSN_PATTERN_FULL.matcher(it).matches() -> SwedishPersonalIdentification(it)
                        SWEDISH_SSN_PATTERN_SHORT.matcher(it).matches() -> SwedishPersonalIdentification("${getCentury(it)}$it")
                        else -> throw RuntimeException("Incorrect format")
                    }
                }

        fun generate(localDate: LocalDate): PersonalIdentification {
            val date: String = localDate.toString().replace("-", "")
            val rnd: String = generateRandom(999).toString().padStart(3, '0')
            val checksum: Int = getChecksumValue("$date$rnd")
            return SwedishPersonalIdentification("$date$rnd$checksum")
        }

        fun generate(year: Year): PersonalIdentification {
            val numberOfDays: Int = if (year.isLeap) 366 else 365
            val localDate: LocalDate = year.atDay(generateRandom(numberOfDays))
            return generate(localDate)
        }
    }
}