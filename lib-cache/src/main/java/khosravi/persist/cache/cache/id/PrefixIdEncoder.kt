package khosravi.persist.cache.cache.id

class PrefixIdEncoder(private val type: String) : IdEncoder<String, String> {

    override fun encode(data: String): String {
        return "${type}_${data}"
    }

    override fun isIdFormatValid(id: String): Boolean = id.startsWith("${type}_")
}