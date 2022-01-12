import java.time.DayOfWeek
import java.time.LocalDate

/**
 * The purpose of this
 */
interface IPersonalIdentification {
    val dateOfBirth: LocalDate
    val weekdayOfBirth: DayOfWeek
    val control: String

    val isValid: Boolean
    val legalGender: LegalGender

    fun getLong(): String
    fun getShort(): String
}

enum class LegalGender {
    FEMALE,
    MALE,
    UNKNOWN
}

interface ICreate {
    fun create(dateOfBirth: LocalDate, control: String): IPersonalIdentification
    fun create(identification: String): IPersonalIdentification
    fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification
}


class DPin(override val dateOfBirth: LocalDate, override val control: String) : IPersonalIdentification {
    override val weekdayOfBirth: DayOfWeek = dateOfBirth.dayOfWeek
    override val isValid: Boolean = false
    override val legalGender: LegalGender = LegalGender.UNKNOWN

    override fun getShort(): String {
        TODO("Not yet implemented")
    }

    override fun getLong(): String {
        TODO("Not yet implemented")
    }

    companion object : ICreate {

        override fun create(dateOfBirth: LocalDate, control: String): IPersonalIdentification =
            DPin(dateOfBirth, control)

        override fun create(identification: String): IPersonalIdentification {
            TODO("Not yet implemented")
        }

        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            TODO("Not yet implemented")
        }
    }
}
