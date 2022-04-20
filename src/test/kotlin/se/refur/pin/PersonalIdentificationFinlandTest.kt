package se.refur.pin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate


class PersonalIdentificationFinlandTest {

    @Test
    fun `incorrect control character format _ exception`() {
        val thrown: Exception = Assertions.assertThrows(
            Exception::class.java,
            { PersonalIdentificationFinland(LocalDate.parse("1966-05-15"), "00000") },
            "Expected init to throw, but it didn't"
        )

        Assertions.assertEquals(thrown.message, "Invalid control format") { "Exception is not thrown" }
    }

    @Test
    fun `incorrect control character _ exception`() {
        val thrown: Exception = Assertions.assertThrows(
            Exception::class.java,
            { PersonalIdentificationFinland(LocalDate.parse("1966-05-15"), "0000") },
            "Expected init to throw, but it didn't"
        )

        Assertions.assertEquals(thrown.message, "Invalid control character") { "Exception is not thrown" }
    }

    @Test
    fun `legalGender _ is MALE`() {
        val pin = PersonalIdentificationFinland(LocalDate.parse("1966-05-15"), "085F")
        val expected = LegalGender.MALE
        Assertions.assertEquals(pin.legalGender, expected) { "${pin.legalGender} is not equal to $expected" }
    }

    @Test
    fun `legalGender _ is FEMALE`() {
        val pin = PersonalIdentificationFinland(LocalDate.parse("1963-10-15"), "398U")
        val expected = LegalGender.FEMALE
        Assertions.assertEquals(pin.legalGender, expected) { "${pin.legalGender} is not equal to $expected" }
    }

    @Test
    fun `shortFormat _ is 150566-085F`() {
        val pin = PersonalIdentificationFinland(LocalDate.parse("1966-05-15"), "085F")
        val expected = "150566-085F"
        Assertions.assertEquals(pin.shortFormat, expected) { "${pin.shortFormat} is not equal to $expected" }
    }

    @Test
    fun `shortFormat _ is 151002+3983`() {
        val pin = PersonalIdentificationFinland(LocalDate.parse("2002-10-15"), "3983")
        val expected = "151002+3983"
        Assertions.assertEquals(pin.shortFormat, expected) { "${pin.shortFormat} is not equal to $expected" }
    }
}
