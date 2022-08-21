package khosravi.persist.cache.cache.id

interface IdEncoder<MODEL, ID> {

    /**
     * Produce Id by given data
     */
    fun encode(data: MODEL): ID

    /**
     * Test decodablity of given [id], return true If can decode otherwise false
     */
    fun test(id: ID): Boolean

}