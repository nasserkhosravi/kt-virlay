package khosravi.persist.cache

interface BasicStore<ID, GET_RETURN_SINGLE, GET_RETURN_LIST, PUT, OP_RETURN> {

    fun get(id: ID): GET_RETURN_SINGLE

    fun getOrNull(id: ID): GET_RETURN_SINGLE?

    fun getAll(): GET_RETURN_LIST

    fun getAllByIds(ids: List<ID>): GET_RETURN_LIST

    fun has(id: ID): Boolean

    fun put(data: PUT): OP_RETURN

    fun putAll(data: List<PUT>): OP_RETURN

    fun remove(id: ID): OP_RETURN

    fun removeAll(ids: List<ID>): OP_RETURN

    fun clear(): OP_RETURN
}