package checksum

/**
 * The purpose of this
 */
interface IChecksum {

    fun isValid(value: String): Boolean

    fun getChecksum(value: String): Int

}