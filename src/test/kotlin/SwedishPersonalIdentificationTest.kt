
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import util.SwedishDateUtil
import java.time.LocalDate


class SwedishPersonalIdentificationTest {

    @Test
    fun isValid() {
        val pin = SwedishPersonalIdentification(SwedishDateUtil.from("1966-05-15"), "0850")
        Assertions.assertTrue(pin.isValid) {
            "${pin.isValid} is not true"
        }
    }

    @Test
    fun legalGenderIsMale() {
        val pin = SwedishPersonalIdentification(SwedishDateUtil.from("1966-05-15"), "0850")
        val actual = LegalGender.MALE
        Assertions.assertEquals(pin.legalGender, actual) {
            "${pin.legalGender} is not equal to $actual"
        }
    }

    @Test
    fun legalGenderIsFemale() {
        val pin = SwedishPersonalIdentification(SwedishDateUtil.from("1963-10-15"), "3983")
        val actual = LegalGender.FEMALE
        Assertions.assertEquals(pin.legalGender, actual) {
            "${pin.legalGender} is not equal to $actual"
        }
    }

    @Test
    fun createFromLocalDate() {
        val pin = SwedishPersonalIdentification.create(LocalDate.parse("1967-07-22"), "5535")
        Assertions.assertTrue(pin.isValid) {
            "generated ${pin.dateOfBirth} is not valid"
        }
    }

    @Test
    fun createFromShortString() {
        val pin = SwedishPersonalIdentification.create("6409213219")
        Assertions.assertTrue(pin.isValid) {
            "generated ${pin.dateOfBirth} is not valid"
        }
    }

    @Test
    fun createFromLongString() {
        val pin = SwedishPersonalIdentification.create("198508017947")
        Assertions.assertTrue(pin.isValid) {
            "generated ${pin.dateOfBirth} is not valid"
        }
    }

    @Test
    fun createFromRandom() {
        val pin = SwedishPersonalIdentification.createRandom(LocalDate.parse("1999-12-31"))
        Assertions.assertTrue(pin.isValid) {
            "generated ${pin.dateOfBirth} is not valid"
        }
    }
}