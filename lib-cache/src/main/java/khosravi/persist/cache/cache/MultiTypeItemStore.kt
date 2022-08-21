package khosravi.persist.cache.cache

import khosravi.persist.cache.adapter.ModelConverter
import khosravi.persist.cache.cache.id.IdEncoder
import java.util.*

class MultiTypeItemStore(
    private val converter: ModelConverter,
    private val cacheStore: CacheStore,
    private val idEncoder: IdEncoder<String,String>
) {

    fun <T> getOrNull(id: String): T? {
        return cacheStore.getOrNull(idEncoder.encode(id))?.toModel()
    }

    fun remove(id: String): Long {
        return cacheStore.remove(id)
    }

    fun <T : IdOwnerString> put(data: T): Long {
        return cacheStore.put(data.toCacheModel(Date()))
    }

    private fun <T : IdOwnerString> CacheModel.toModel(): T {
        return converter.deserialize(value)
    }

    private fun <T : IdOwnerString> T.toCacheModel(date: Date): CacheModel {
        val value = converter.serialize(this)
        return CacheModel(idEncoder.encode(getIdValue()), value, date.time)
    }
}