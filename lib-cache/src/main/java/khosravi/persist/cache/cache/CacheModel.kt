package khosravi.persist.cache.cache

open class CacheModel(
    val id: String,
    val value: String,
    val lastTimeStamp: Long,
) : IdOwner<String> {

    override fun getIdValue(): String = id
}

