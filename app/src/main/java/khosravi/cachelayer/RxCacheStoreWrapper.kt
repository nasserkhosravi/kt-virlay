package khosravi.cachelayer

import io.reactivex.Observable
import khosravi.persist.cache.CacheModel
import khosravi.persist.cache.CacheStore
import khosravi.persist.cache.CacheStoreWrapper

class RxCacheStoreWrapper(private val cacheStore: CacheStore) : CacheStoreWrapper<
        Observable<CacheModel>,
        Observable<Optional<CacheModel>>,
        Observable<List<CacheModel>>,
        Observable<Boolean>,
        Observable<Long>
        > {

    override fun get(id: String): Observable<CacheModel> {
        return Observable.create {
            it.onNext(cacheStore.get(id))
            it.onComplete()
        }
    }

    override fun getOrNull(id: String): Observable<Optional<CacheModel>> {
        return Observable.create {
            it.onNext(cacheStore.getOrNull(id).toOptional())
            it.onComplete()
        }
    }

    override fun has(id: String): Observable<Boolean> {
        return Observable.create {
            it.onNext(cacheStore.has(id))
            it.onComplete()
        }
    }

    override fun getAll(): Observable<List<CacheModel>> {
        return Observable.create {
            it.onNext(cacheStore.getAll())
            it.onComplete()
        }
    }

    override fun getAllByIds(ids: List<String>): Observable<List<CacheModel>> {
        return Observable.create {
            it.onNext(cacheStore.getAllByIds(ids))
            it.onComplete()
        }
    }

    override fun put(data: CacheModel): Observable<Long> {
        return Observable.create {
            it.onNext(cacheStore.put(data))
            it.onComplete()
        }
    }

    override fun putAll(data: List<CacheModel>): Observable<Long> {
        return Observable.create {
            it.onNext(cacheStore.putAll(data))
            it.onComplete()
        }
    }


}