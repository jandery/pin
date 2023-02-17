package se.refur.pin.checksum

/**
 * The purpose of this interface
 */
interface IChecksum {

    fun isValid(value: String): Boolean

    fun getChecksumAsInt(value: String): Int =
        throw Exception("Not used")

    fun getChecksumAsChar(value: String): Char =
        throw Exception("Not used")

}