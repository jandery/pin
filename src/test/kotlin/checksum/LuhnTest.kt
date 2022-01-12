package checksum

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LuhnTest {
    // SSN from fejk.se
    private val listOfValidSwedishSsn = listOf(
        "6605150850", "7902094791", "0202204392", "6310153983", "7908199214",
        "6707225535", "8705138256", "8508017947", "8706278168", "6409213219"
    )

    private val listOfInvalidSsn = listOf(
        "6707225530", "6707225531", "6707225532", "6707225533", "6707225534",
        "6605150855", "6605150856", "6605150857", "6605150858", "6605150859"
    )

    @Test
    fun testValidListOfSsn() {
        listOfValidSwedishSsn.forEach {
            assertTrue(Luhn.isValid(it))
        }
    }

    @Test
    fun testInValidListOfSsn() {
        listOfInvalidSsn.forEach {
            assertFalse(Luhn.isValid(it))
        }
    }

}