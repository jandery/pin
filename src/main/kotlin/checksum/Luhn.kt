package checksum

/**
 * Calculate the checksum according to
 * https://en.wikipedia.org/wiki/Luhn_algorithm
 */
object Luhn : IChecksum {

    override fun isValid(value: String): Boolean {
        val checksum = getChecksum(value.substring(0, value.length-1))
        val lastCharacter = value.substring(value.length-1, value.length)
        return checksum == Integer.parseInt(lastCharacter)
    }

    override fun getChecksum(value: String): Int {
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

fun main() {
    val str = "abcdef"

    println(str.substring(0, str.length-1))
    println(str.substring(str.length-1, str.length))

}