package khosravi.persist.cache.cache

interface IdOwner<Type> {
    fun getIdValue(): Type
}

typealias IdOwnerString = IdOwner<String>