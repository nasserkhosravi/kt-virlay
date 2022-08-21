package khosravi.persist.cache.cache

import android.content.SharedPreferences
import khosravi.persist.cache.adapter.SingleModelConverter
import khosravi.persist.cache.cache.id.IdEncoder
import khosravi.persist.cache.cache.id.PrefixIdEncoder

class SharedPreferenceSingleItemStore<T>(
    private val id: String,
    private val store: SharedPreferences,
    private val converter: SingleModelConverter<T>,
    private val idCoder: IdEncoder<String, String> = PrefixIdEncoder(id),
) : SingleItemStore<T> {

    override fun get(): T = getOrNull()!!

    override fun getOrNull(): T? {
        return store.getString(idCoder.encode(id), null)?.let {
            converter.deserialize(it)
        }
    }

    override fun has(): Boolean = store.contains(idCoder.encode(id))

    override fun remove(): Boolean {
        return store.edit().remove(idCoder.encode(id)).commit()
    }

    override fun put(data: T): Boolean {
        val serialized = converter.serialize(data)
        return store.edit().putString(idCoder.encode(id), serialized).commit()
    }
}