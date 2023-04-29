package khosravi.persist.cache.id

interface IdEncoder<MODEL, ID> {

    /**
     * Produce Id by given data
     */
    fun encode(data: MODEL): ID

    /**
     * Check validity format of given [id], return true If is valid otherwise false
     */
    fun isIdFormatValid(id: ID): Boolean

}