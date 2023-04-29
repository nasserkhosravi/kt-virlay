package khosravi.persist.cache

interface CacheStoreWrapper<TYPE, OPTIONAL, GET_ALL, HAS, PUT> {

    fun get(id: String): TYPE
    fun getOrNull(id: String): OPTIONAL
    fun has(id: String): HAS
    fun getAll(): GET_ALL
    fun getAllByIds(ids: List<String>): GET_ALL
    fun put(data: CacheModel): PUT
    fun putAll(data: List<CacheModel>): PUT
}

