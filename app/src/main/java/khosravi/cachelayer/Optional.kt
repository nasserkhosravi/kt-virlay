package khosravi.cachelayer

import java.util.*

/**
 * Mimic [java.util.Optional] API, the existence reason of this class is that
 * [java.util.Optional] started to support in API 26
 */
class Optional<T>(val value: T?) {

    fun isPresent() = value != null

    fun get(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }
}

fun <T : Any> T?.toOptional(): Optional<T> {
    return Optional(this)
}