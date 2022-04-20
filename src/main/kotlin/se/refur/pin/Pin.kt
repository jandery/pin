package se.refur.pin

import se.refur.pin.checksum.Luhn
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

/**
 * The purpose of this
 */
enum class Pin {
    SWEDEN {
        override fun create(birthDate: LocalDate, control: String): IPersonalIdentification =
            SwedishPersonalIdentification(birthDate, control)

        override fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification {
            val rnd: String = generateRandom(999).toString().padStart(3, '0')
            val checksum: Int = Luhn.getChecksum("${dateOfBirth.toShortString()}$rnd")

            TODO("Not yet implemented")
        }
    };

    abstract fun create(birthDate: LocalDate, control: String): IPersonalIdentification

    abstract fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification

    companion object {
        private fun generateRandom(maxValue: Int): Int =
            ThreadLocalRandom.current().nextInt(0, maxValue)
    }
}