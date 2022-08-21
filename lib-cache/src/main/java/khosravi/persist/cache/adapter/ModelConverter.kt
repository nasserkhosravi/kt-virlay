package khosravi.persist.cache.adapter

interface ModelConverter {
    fun <T> serialize(value: T): String
    fun <T> deserialize(value: String): T
}