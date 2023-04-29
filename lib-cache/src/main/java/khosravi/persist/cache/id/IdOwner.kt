package khosravi.persist.cache.id

interface IdOwner<Type> {
    fun getIdValue(): Type
}

typealias IdOwnerString = IdOwner<String>