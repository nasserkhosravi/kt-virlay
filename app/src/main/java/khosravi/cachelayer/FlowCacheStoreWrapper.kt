package khosravi.cachelayer

import khosravi.persist.cache.CacheModel
import khosravi.persist.cache.CacheStore
import khosravi.persist.cache.CacheStoreWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlowCacheStoreWrapper(private val cacheStore: CacheStore) : CacheStoreWrapper<
        Flow<CacheModel>,
        Flow<CacheModel?>,
        Flow<List<CacheModel>>,
        Flow<Boolean>,
        Flow<Long>
        > {

    override fun get(id: String) = flow { emit(cacheStore.get(id)) }

    override fun getOrNull(id: String) = flow { emit(cacheStore.getOrNull(id)) }

    override fun has(id: String) = flow { emit(cacheStore.has(id)) }

    override fun getAll() = flow { emit(cacheStore.getAll()) }

    override fun getAllByIds(ids: List<String>) = flow { emit(cacheStore.getAllByIds(ids)) }

    override fun put(data: CacheModel) = flow { emit(cacheStore.put(data)) }

    override fun putAll(data: List<CacheModel>) = flow { emit(cacheStore.putAll(data)) }


}