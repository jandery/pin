package se.refur.pin.checksum

/**
 * The purpose of this
 */
object Modulo31 : IChecksum  {
    private val CONTROL_CHARS = listOf(
        '0','1','2','3','4','5','6','7','8','9',
        'A','B','C','D','E','F','H','J','K','L',
        'M','N','P','R','S','T','U','V','W','X','Y')


    override fun isValid(value: String): Boolean = try {
        val index = value.substring(0, 9).toLong() % 31
        CONTROL_CHARS[index.toInt()] == value.last()
    } catch (e: Exception) {
        false
    }

    override fun getChecksumAsChar(value: String): Char {
        val index = value.toLong() % 31
        return CONTROL_CHARS[index.toInt()]
    }
}