package khosravi.cachelayer

import khosravi.persist.cache.cache.CacheModel
import khosravi.persist.cache.cache.CacheStore
import org.dizitart.no2.Document
import org.dizitart.no2.Filter
import org.dizitart.no2.NitriteCollection
import org.dizitart.no2.filters.Filters

class NitriteCacheStore(private val collection: NitriteCollection) : CacheStore {

    companion object {
        private const val KEY_ID = "id"
        private const val KEY_VALUE = "v"
        private const val KEY_TIMESTAMP = "ts"
    }

    override fun get(id: String): CacheModel = getOrNull(id)!!

    override fun getOrNull(id: String): CacheModel? {
        return collection.find().firstOrNull { it[KEY_ID] == id }?.let {
            toCacheModel(it)
        }
    }

    private fun toCacheModel(it: Document) = CacheModel(it[KEY_ID] as String, it[KEY_VALUE] as String, it[KEY_TIMESTAMP] as Long)

    override fun getAll(): List<CacheModel> {
        return collection.find().map { toCacheModel(it) }
    }

    override fun getAllByIds(ids: List<String>): List<CacheModel> {
        return collection.find(Filters.`in`(KEY_ID, ids)).map { toCacheModel(it) }
    }

    override fun has(id: String): Boolean {
        return collection.find(Filters.eq(KEY_ID, id)).size() > 0
    }

    override fun put(data: CacheModel): Long {
        val doc = Document().apply {
            put(KEY_ID, data.id)
            put(KEY_VALUE, data.value)
            put(KEY_TIMESTAMP, data.lastTimeStamp)
        }
        return collection.update(doc, true).affectedCount.toLong()
    }

    override fun putAll(data: List<CacheModel>): Long {
        return data.sumOf { put(it) }
    }

    override fun remove(id: String): Long {
        return collection.remove(Filters.eq(KEY_ID, id)).affectedCount.toLong()
    }

    override fun removeAll(ids: List<String>): Long {
        return collection.remove(Filters.`in`(KEY_ID, ids)).affectedCount.toLong()
    }

    override fun clear(): Long {
        val filter: Filter? = null
        return collection.remove(filter).affectedCount.toLong()
    }
}