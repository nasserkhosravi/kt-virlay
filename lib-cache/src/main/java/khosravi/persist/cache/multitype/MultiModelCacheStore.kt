package khosravi.persist.cache.multitype

import khosravi.persist.cache.adapter.ModelConverter
import khosravi.persist.cache.CacheModel
import khosravi.persist.cache.CacheStore
import khosravi.persist.cache.id.IdEncoder
import khosravi.persist.cache.id.IdOwnerString
import java.util.*

class MultiModelCacheStore(
    private val cacheStore: CacheStore,
    private val converter: ModelConverter,
    private val idCoder: IdEncoder<String, String>? = null
) {

    fun <T : IdOwnerString> getOrNull(id: String): T? {
        return cacheStore.getOrNull(idCoder?.encode(id) ?: id)?.toModel()
    }

    fun <T : IdOwnerString> get(id: String): T {
        return cacheStore.get(idCoder?.encode(id) ?: id).toModel()
    }

    fun <T> getAll(): List<T> {
        return cacheStore.getAll().let { list ->
            if (idCoder != null) list.filter { idCoder.isIdFormatValid(it.id) }
            else list
        }.map { it.toModel() }
    }

    fun <T : IdOwnerString> getAllByIds(ids: List<String>): List<T> {
        val fIds = idCoder?.let { ids.map { idCoder.encode(it) } } ?: ids
        return cacheStore.getAllByIds(fIds).map { it.toModel() }
    }

    fun has(id: String): Boolean {
        return cacheStore.has(idCoder?.encode(id) ?: id)
    }

    fun <T : IdOwnerString> put(data: T): Long {
        return cacheStore.put(data.toCacheModel(Date()))
    }

    fun <T : IdOwnerString> putAll(data: List<T>): Long {
        val list = data.map { it.toCacheModel(Date()) }
        return cacheStore.putAll(list)
    }

    fun remove(id: String): Long {
        return cacheStore.remove(idCoder?.encode(id) ?: id)
    }

    fun removeAll(ids: List<String>): Long {
        val fIds = idCoder?.let { ids.map { id -> idCoder.encode(id) } } ?: ids
        return cacheStore.removeAll(fIds)
    }

    fun clear(): Long {
        val candidate = cacheStore.getAll()
            .let { list ->
                idCoder?.let {
                    list.filter { idCoder.isIdFormatValid(it.id) }
                } ?: list
            }
            .map { it.id }
        return cacheStore.removeAll(candidate)
    }

    private fun <T : IdOwnerString> CacheModel.toModel(): T {
        return converter.deserialize(value)
    }

    private fun <T : IdOwnerString> T.toCacheModel(date: Date): CacheModel {
        val id = idCoder?.encode(this.getIdValue()) ?: this.getIdValue()
        val value = converter.serialize(this)
        return CacheModel(id, value, date.time)
    }

}