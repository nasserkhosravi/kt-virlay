package khosravi.persist.cache.cache

import khosravi.persist.cache.adapter.SingleModelConverter
import khosravi.persist.cache.cache.id.IdEncoder
import khosravi.persist.cache.cache.id.PrefixIdEncoder
import java.util.*

class ModelCacheStore<T> constructor(
    private val cacheStore: CacheStore,
    private val converter: SingleModelConverter<T>,
    private val idCoder: IdEncoder<String, String>,
) : BasicStore<String, T, T, Long> where T : IdOwner<String> {

    override fun get(id: String): T {
        return cacheStore.get(idCoder.encode(id)).toModel()
    }

    override fun getOrNull(id: String): T? {
        return cacheStore.getOrNull(idCoder.encode(id))?.toModel()
    }

    override fun getAll(): List<T> {
        return cacheStore.getAll()
            .filter { idCoder.test(it.id) }
            .map { it.toModel() }
    }

    override fun getAllByIds(ids: List<String>): List<T> {
        return cacheStore.getAllByIds(
            ids.map { idCoder.encode(it) }
        ).map { it.toModel() }
    }

    override fun has(id: String): Boolean {
        return cacheStore.has(idCoder.encode(id))
    }

    override fun put(data: T): Long {
        return cacheStore.put(data.toCacheModel(Date()))
    }

    override fun putAll(data: List<T>): Long {
        val list = data.map { it.toCacheModel(Date()) }
        return cacheStore.putAll(list)
    }

    override fun remove(id: String): Long {
        return cacheStore.remove(idCoder.encode(id))
    }

    override fun removeAll(ids: List<String>): Long {
        val fIds = ids.map { id -> idCoder.encode(id) }
        return cacheStore.removeAll(fIds)
    }

    override fun clear(): Long {
        val candidate = cacheStore.getAll()
            .filter { idCoder.test(it.id) }
            .map { it.id }
        return cacheStore.removeAll(candidate)
    }

    private fun CacheModel.toModel(): T {
        return converter.deserialize(value)
    }

    private fun T.toCacheModel(date: Date): CacheModel {
        val id = idCoder.encode(this.getIdValue())
        val value = converter.serialize(this)
        return CacheModel(id, value, date.time)
    }

    class Factory constructor(
        private val cacheStore: CacheStore
    ) {
        fun <T : IdOwner<String>> create(id: String, converter: SingleModelConverter<T>): ModelCacheStore<T> {
            return ModelCacheStore(cacheStore, converter, PrefixIdEncoder(id))
        }
    }

}
