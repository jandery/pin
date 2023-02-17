package se.refur.pin

/**
 * The purpose of this enum is holding legal genders.
 */
enum class LegalGender {
    FEMALE,
    MALE,
    UNKNOWN;

    companion object {
        fun evenForFemale(char: Char): LegalGender = when {
            Character.getNumericValue(char) % 2 == 0 -> FEMALE
            else -> MALE
        }
    }
}