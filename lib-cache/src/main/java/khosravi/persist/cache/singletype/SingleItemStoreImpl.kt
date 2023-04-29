package khosravi.persist.cache.singletype

import khosravi.persist.cache.adapter.SingleModelConverter
import khosravi.persist.cache.CacheModel
import khosravi.persist.cache.CacheStore
import java.util.*

open class SingleItemStoreImpl<T>(
    private val id: String,
    private val cacheStore: CacheStore,
    private val converter: SingleModelConverter<T>,
) : SingleItemStore<T> {

    override fun getOrNull(): T? {
        return cacheStore.getOrNull(id)?.toModel()
    }

    override fun remove(): Boolean {
        cacheStore.remove(id)
        return true
    }

    override fun put(data: T): Boolean {
        cacheStore.put(data.toCacheModel(Date()))
        return true
    }

    private fun CacheModel.toModel(): T {
        return converter.deserialize(value)
    }

    private fun T.toCacheModel(date: Date): CacheModel {
        val value = converter.serialize(this)
        return CacheModel(id, value, date.time)
    }

    override fun get(): T = getOrNull()!!

    override fun has(): Boolean = cacheStore.has(id)
}