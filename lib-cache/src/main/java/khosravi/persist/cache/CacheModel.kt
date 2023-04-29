package khosravi.persist.cache

import khosravi.persist.cache.id.IdOwner

open class CacheModel(
    val id: String,
    val value: String,
    val lastTimeStamp: Long,
) : IdOwner<String> {

    override fun getIdValue(): String = id
}

