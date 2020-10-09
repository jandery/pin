/**
 * The purpose of this
 */
interface PersonalIdentification {

    val isValid: Boolean

    val isMale: Boolean

    val isFemale: Boolean

    fun getFormatted(): String

}