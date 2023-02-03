package khosravi.persist.cache.cache

import android.content.SharedPreferences
import khosravi.persist.cache.cache.id.IdEncoder
import khosravi.persist.cache.cache.id.PrefixIdEncoder
import org.json.JSONObject

class SharedPreferenceStore(
    private val id: String,
    private val store: SharedPreferences,
    private val idCoder: IdEncoder<String, String> = PrefixIdEncoder(id),
) : CacheStore {

    companion object{
        private const val KEY_ID = "id"
        private const val KEY_VALUE = "v"
        private const val KEY_TIMESTAMP = "ts"
        const val UN_AFFECTED = -1L
    }

    override fun get(id: String): CacheModel = getOrNull(id)!!

    override fun getOrNull(id: String): CacheModel? {
        return store.getString(idCoder.encode(id), null)?.let { toCacheModel(it) }
    }

    private fun toCacheModel(jsonStr: String): CacheModel {
        val json = JSONObject(jsonStr)
        val id = json.getString(KEY_ID)
        val value = json.getString(KEY_VALUE)
        val timeStamp = json.getLong(KEY_TIMESTAMP)
        return CacheModel(id, value, timeStamp)
    }

    override fun getAll(): List<CacheModel> {
        return store.all.mapNotNull {
            toModelByEntry(it)
        }
    }

    private fun toModelByEntry(it: Map.Entry<String, Any?>): CacheModel? {
        val value = it.value as? String
        return if (value != null) {
            toCacheModel(value)
        } else null
    }

    override fun getAllByIds(ids: List<String>): List<CacheModel> {
        return store.all.filter { ids.contains(idCoder.encode(it.key)) }.mapNotNull {
            toModelByEntry(it)
        }
    }

    override fun has(id: String): Boolean = store.contains(idCoder.encode(id))

    override fun put(data: CacheModel): Long {
        val editor = store.edit()
        put(data, editor)
        editor.apply()
        return 1
    }

    private fun put(data: CacheModel, editor: SharedPreferences.Editor) {
        val json = JSONObject().apply {
            put(KEY_ID, data.id)
            put(KEY_VALUE, data.value)
            put(KEY_TIMESTAMP, data.lastTimeStamp)
        }.toString()
        editor.putString(idCoder.encode(id), json)
    }

    override fun putAll(data: List<CacheModel>): Long {
        val editor = store.edit()
        data.forEach {
            put(it, editor)
        }
        editor.apply()
        return data.size.toLong()
    }

    override fun remove(id: String): Long {
        val realId = idCoder.encode(id)
        if (store.contains(realId)) {
            return UN_AFFECTED
        }
        store.edit().remove(realId).apply()
        return 1
    }

    override fun removeAll(ids: List<String>): Long {
        val edit = store.edit()
        var successCount = 0L
        ids.forEach {
            val realId = idCoder.encode(it)
            if (store.contains(realId)) {
                return UN_AFFECTED
            }
            edit.remove(realId)
            successCount++
        }
        edit.apply()
        return successCount
    }

    override fun clear(): Long {
        val edit = store.edit()
        val all = store.all.filter { idCoder.test(it.key) }
        all.forEach {
            edit.remove(idCoder.encode(it.key))
        }
        edit.apply()
        return all.size.toLong()
    }

}