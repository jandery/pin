package se.refur.pin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PersonalIdentificationSwedenTest {

    @Test
    fun `incorrect control character format _ exception`() {
        val thrown: Exception = Assertions.assertThrows(
            Exception::class.java,
            { PersonalIdentificationSweden(LocalDate.parse("1966-05-15"), "00000") },
            "Expected init to throw, but it didn't"
        )

        Assertions.assertEquals(thrown.message, "Invalid control format") { "Exception is not thrown" }
    }

    @Test
    fun `incorrect control character _ exception`() {
        val thrown: Exception = Assertions.assertThrows(
            Exception::class.java,
            { PersonalIdentificationSweden(LocalDate.parse("1966-05-15"), "0000") },
            "Expected init to throw, but it didn't"
        )

        Assertions.assertEquals(thrown.message, "Invalid control character") { "Exception is not thrown" }
    }

    @Test
    fun `legalGender _ is MALE`() {
        val pin = PersonalIdentificationSweden(LocalDate.parse("1966-05-15"), "0850")
        val expected = LegalGender.MALE
        Assertions.assertEquals(pin.legalGender, expected) { "${pin.legalGender} is not equal to $expected" }
    }

    @Test
    fun `legalGender _ is FEMALE`() {
        val pin = PersonalIdentificationSweden(LocalDate.parse("1963-10-15"), "3983")
        val expected = LegalGender.FEMALE
        Assertions.assertEquals(pin.legalGender, expected) { "${pin.legalGender} is not equal to $expected" }
    }

    @Test
    fun `shortFormat _ is 660515-0850`() {
        val pin = PersonalIdentificationSweden(LocalDate.parse("1966-05-15"), "0850")
        val expected = "660515-0850"
        Assertions.assertEquals(pin.shortFormat, expected) { "${pin.shortFormat} is not equal to $expected" }
    }

    @Test
    fun `shortFormat _ is 200515+0855`() {
        val pin = PersonalIdentificationSweden(LocalDate.parse("1920-05-15"), "0855")
        val expected = "200515+0855"
        Assertions.assertEquals(pin.shortFormat, expected) { "${pin.shortFormat} is not equal to $expected" }
    }

    @Test
    fun `longFormat _ is 19631015-3983`() {
        val pin = PersonalIdentificationSweden(LocalDate.parse("1963-10-15"), "3983")
        val expected = "19631015-3983"
        Assertions.assertEquals(pin.longFormat, expected) { "${pin.longFormat} is not equal to $expected" }
    }

    @Test
    fun `isValid _ empty string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ without sign and to short string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515085")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ without sign and to long string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("1966051508500")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ without sign and matching string _ true`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("196605150850")
        Assertions.assertTrue(actual)
    }

    @Test
    fun `isValid _ with minus sign and to short string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515-085")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ with minus sign and to long string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515-08500")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ with minus sign and matching string _ true`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515-0850")
        Assertions.assertTrue(actual)
    }

    @Test
    fun `isValid _ with plus sign and to short string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515+085")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ with plus sign and to long string _ false`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515+08500")
        Assertions.assertFalse(actual)
    }

    @Test
    fun `isValid _ with plus sign and matching string _ true`() {
        val actual: Boolean = PersonalIdentificationSweden.isValid("19660515+0850")
        Assertions.assertTrue(actual)
    }
}