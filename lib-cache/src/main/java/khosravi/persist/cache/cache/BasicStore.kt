package khosravi.persist.cache.cache

interface BasicStore<ID, GET_RETURN, PUT_DATA, RETURN> {

    fun get(id: ID): GET_RETURN

    fun getOrNull(id: ID): GET_RETURN?

    fun getAll(): List<GET_RETURN>

    fun getAllByIds(ids: List<ID>): List<GET_RETURN>

    fun has(id: ID): Boolean

    fun put(data: PUT_DATA): RETURN

    fun putAll(data: List<PUT_DATA>): RETURN

    fun remove(id: ID): RETURN

    fun removeAll(ids: List<ID>): RETURN

    fun clear(): RETURN
}