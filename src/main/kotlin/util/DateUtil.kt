package util

import java.lang.RuntimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

/**
 * The purpose of this
 */
interface IDateUtil {
    val date: LocalDate
    val formatForChecksum: String
}


internal class SwedishDateUtil(override val date: LocalDate): IDateUtil {
    override val formatForChecksum: String =
        date.format(DateTimeFormatter.ofPattern("yyMMdd"))

    companion object {
        // Valid format for YYYYMMDDXXXX. Must start with 19 or 20 and followed by 6+4 numeric characters
        private val SWEDISH_SSN_PATTERN_FULL = Pattern.compile("^(19|20)[0-9]{6}$")

        // Valid format for YYMMDDXXXX. Contains 6+4 numeric characters
        private val SWEDISH_SSN_PATTERN_SHORT = Pattern.compile("^[0-9]{6}$")

        fun from(date: LocalDate): IDateUtil =
            SwedishDateUtil(date)

        fun from(date: String): IDateUtil =
            date.replace("-", "").replace("+", "").let {
                when {
                    SWEDISH_SSN_PATTERN_FULL.matcher(it).matches() ->
                        SwedishDateUtil(LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyyMMdd")))

                    SWEDISH_SSN_PATTERN_SHORT.matcher(it).matches() ->
                        SwedishDateUtil(LocalDate.parse(it, DateTimeFormatter.ofPattern("yyMMdd")))

                    else -> throw RuntimeException("Incorrect format")
                }
            }
    }
}

fun main() {
    val identification = "196509033939"
    println(identification.substring(0, identification.length-4))
    println(identification.substring(identification.length-4))
}