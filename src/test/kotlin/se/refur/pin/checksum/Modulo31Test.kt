package se.refur.pin.checksum

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class Modulo31Test {

    private val listOfValidSsn = listOf(
        "150566085F", "151063398U", "1510023983"
    )

    private val listOfInvalidSsn = listOf(
        "1505660000"
    )


    @Test
    fun testValidListOfSsn() {
        listOfValidSsn.forEach {
            Assertions.assertTrue(Modulo31.isValid(it))
        }
    }

    @Test
    fun testInValidListOfSsn() {
        listOfInvalidSsn.forEach {
            Assertions.assertFalse(Modulo31.isValid(it))
        }
    }
}