import checksum.Luhn
import util.IDateUtil
import util.SwedishDateUtil
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import java.util.regex.Pattern

/**
 * Validate a Swedish Personal Identification number (personnummer)
 */

private fun LocalDate.toShortString(): String =
    this.format(DateTimeFormatter.ofPattern("yy-MM-dd"))
        .replace("-", "")


class SwedishPersonalIdentification(private val dateUtil: IDateUtil, override val control: String) :
    IPersonalIdentification {

    init {
        if (!Pattern.matches("[0-9]{4}", control)) {
            throw Exception("Invalid control characters")
        }
    }

    override val dateOfBirth: LocalDate by lazy {
        dateUtil.date
    }

    override val weekdayOfBirth: DayOfWeek by lazy {
        dateUtil.date.dayOfWeek
    }

    override val isValid: Boolean by lazy {
        Luhn.isValid("${dateOfBirth.toShortString()}$control")
    }

    override val legalGender: LegalGender by lazy {
        when {
            Character.getNumericValue(control[2]) % 2 == 0 -> LegalGender.FEMALE
            else -> LegalGender.MALE
        }
    }

    override fun getShort(): String {
        return ""
    }

    override fun getLong(): String {
        return ""
    }

    companion object : ICreate {
        private fun generateRandom(maxValue: Int): Int =
            ThreadLocalRandom.current().nextInt(0, maxValue)

        override fun create(dateOfBirth: LocalDate, control: String): IPersonalIdentification =
            SwedishPersonalIdentification(SwedishDateUtil.from(dateOfBirth), control)

        override fun create(identification: String): IPersonalIdentification =
            SwedishPersonalIdentification(
                SwedishDateUtil.from(identification.substring(0, identification.length - 4)),
                identification.substring(identification.length - 4)
            )

        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            val rnd: String = generateRandom(999).toString().padStart(3, '0')
            val checksum: Int = Luhn.getChecksum("${dateOfBirth.toShortString()}$rnd")
            return SwedishPersonalIdentification(SwedishDateUtil.from(dateOfBirth), "$rnd$checksum")
        }
    }
}