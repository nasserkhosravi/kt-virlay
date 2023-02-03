package khosravi.cachelayer

import com.google.gson.Gson
import khosravi.persist.cache.adapter.SingleModelConverter

class GsonUserModelConverter(private val gson: Gson) : SingleModelConverter<User> {
    override fun serialize(value: User): String = gson.toJson(value)
    override fun deserialize(value: String): User = gson.fromJson(value, User::class.java)
}