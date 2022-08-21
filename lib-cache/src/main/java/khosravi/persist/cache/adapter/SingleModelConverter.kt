package khosravi.persist.cache.adapter

interface SingleModelConverter<T> {
    fun serialize(value: T): String
    fun deserialize(value: String): T
}