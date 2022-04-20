package se.refur.pin

import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

/**
 * The purpose of this
 */
interface ICreate {

    fun isValid(birthDate: LocalDate, control: String): Boolean

    fun create(birthDate: LocalDate, control: String): IPersonalIdentification

    fun createRandom(dateOfBirth: LocalDate): IPersonalIdentification

    fun generateRandom(maxValue: Int): Int =
        ThreadLocalRandom.current().nextInt(0, maxValue)
}