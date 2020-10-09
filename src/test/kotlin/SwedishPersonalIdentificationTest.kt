
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Year


class SwedishPersonalIdentificationTest {

    @Test
    fun formatValid_tooLong_false() {
        Assertions.assertFalse(SwedishPersonalIdentification("1993123022691").isValid)
    }

    @Test
    fun formatValid_tooShort_false() {
        Assertions.assertFalse(SwedishPersonalIdentification("19890911576").isValid)
    }

    @Test
    fun formatValid_containingLetter_false() {
        Assertions.assertFalse(SwedishPersonalIdentification("19710101s000").isValid)
    }

    @Test
    fun formatValid_correct_true() {
        Assertions.assertTrue(SwedishPersonalIdentification.create("195507078755").isValid)
    }

    @Test
    fun checksum_valid_true() {
        Assertions.assertTrue(SwedishPersonalIdentification.create("198011035188").isValid)
    }

    @Test
    fun checksum_invalid_false() {
        Assertions.assertFalse(SwedishPersonalIdentification.create("198011035189").isValid)
    }

    @Test
    fun checksum_endsWith0_valid() {
        Assertions.assertTrue(SwedishPersonalIdentification.create("197306195640").isValid)
    }

    @Test
    fun isMale_male_true() {
        Assertions.assertTrue(SwedishPersonalIdentification.create("195507078755").isMale)
    }

    @Test
    fun isMale_male_false() {
        Assertions.assertFalse(SwedishPersonalIdentification.create("195507078755").isFemale)
    }

    @Test
    fun isFemale_female_false() {
        Assertions.assertFalse(SwedishPersonalIdentification.create("197306195640").isMale)
    }

    @Test
    fun isFemale_female_true() {
        Assertions.assertTrue(SwedishPersonalIdentification.create("197306195640").isFemale)
    }

    @Test
    fun formatted_195507078755() {
        Assertions.assertEquals(SwedishPersonalIdentification.create("195507078755").getFormatted(), "19550707-8755")
    }

    @Test
    fun formatted_197306195640() {
        Assertions.assertEquals(SwedishPersonalIdentification.create("197306195640").getFormatted(), "19730619-5640")
    }

    @Test
    fun formatted_191806195648() {
        Assertions.assertEquals(SwedishPersonalIdentification.create("191806195648").getFormatted(), "19180619+5648")
    }

    @Test
    fun generate_LocalDateNow_isValid() {
        Assertions.assertTrue(SwedishPersonalIdentification.generate(LocalDate.now()).isValid)
    }

    @Test
    fun generate_LocalDateParse_isValid() {
        Assertions.assertTrue(SwedishPersonalIdentification.generate(LocalDate.parse("1965-09-03")).isValid)
    }

    @Test
    fun generate_YearNow_isValid() {
        Assertions.assertTrue(SwedishPersonalIdentification.generate(Year.now()).isValid)
    }

    @Test
    fun generate_YearParse_isValid() {
        Assertions.assertTrue(SwedishPersonalIdentification.generate(Year.parse("1966")).isValid)
    }
}