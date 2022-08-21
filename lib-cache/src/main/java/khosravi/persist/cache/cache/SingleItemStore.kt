package khosravi.persist.cache.cache

interface SingleItemStore<T> {

    fun get(): T

    fun getOrNull(): T?

    fun has(): Boolean

    fun remove(): Boolean

    fun put(data: T): Boolean
}