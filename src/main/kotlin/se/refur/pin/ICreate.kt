package se.refur.pin

import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

/**
 * The purpose of this interface is to create a personal identification number
 */
interface ICreate {

    /**
     * Test if Birth date with control characters is valid
     */
    fun isValid(birthDate: LocalDate, control: String): Boolean

    /**
     * Test if personal identification number is valid
     */
    fun isValid(pin: String): Boolean

    /**
     * Create from birth date and control characters
     */
    fun create(birthDate: LocalDate, control: String): IPersonalIdentification

    /**
     * Create from personal identification number
     */
    fun create(pin: String): IPersonalIdentification

    /**
     * Create random from birth date
     */
    fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification

    /**
     * Random generator
     */
    fun generateRandom(maxValue: Int): Int =
        ThreadLocalRandom.current().nextInt(0, maxValue)
}