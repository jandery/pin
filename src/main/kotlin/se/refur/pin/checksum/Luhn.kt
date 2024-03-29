package se.refur.pin.checksum

/**
 * he purpose of this object is to calculate the checksum according to
 * https://en.wikipedia.org/wiki/Luhn_algorithm
 */
object Luhn : IChecksum {

    override fun isValid(value: String): Boolean {
        val checksum = getChecksumAsInt(value.take(value.length-1))
        val lastCharacter = value.takeLast(1)
        return checksum == Integer.parseInt(lastCharacter)
    }

    override fun getChecksumAsInt(value: String): Int {
        val checksum: Int = calculateLuhnChecksum(value)
        return (10 - checksum % 10) % 10
    }

    private fun calculateLuhnChecksum(str: String): Int =
        str.mapIndexed { index, c ->
            val multiplier: Int = if (index % 2 == 0) 2 else 1
            val value: Int = Character.getNumericValue(c)
            if (value < 9) (value * multiplier) % 9 else 9
        }
            .sum()
}