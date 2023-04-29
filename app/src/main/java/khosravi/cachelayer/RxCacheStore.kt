package khosravi.cachelayer

import io.reactivex.Observable
import khosravi.persist.cache.CacheModel
import khosravi.persist.cache.CacheStore

class RxCacheStore(private val cacheStore: CacheStore) {

    fun get(id: String): Observable<CacheModel> {
        return Observable.create {
            it.onNext(cacheStore.get(id))
            it.onComplete()
        }
    }

    fun getOrNull(id: String): Observable<Optional<CacheModel>> {
        return Observable.create {
            it.onNext(cacheStore.getOrNull(id).toOptional())
            it.onComplete()
        }
    }

    fun has(id: String): Observable<Boolean> {
        return Observable.create {
            it.onNext(cacheStore.has(id))
            it.onComplete()
        }
    }

    fun getAll(): Observable<List<CacheModel>> {
        return Observable.create {
            it.onNext(cacheStore.getAll())
            it.onComplete()
        }
    }

    fun getAllByIds(ids: List<String>): Observable<List<CacheModel>> {
        return Observable.create {
            it.onNext(cacheStore.getAllByIds(ids))
            it.onComplete()
        }
    }

    fun put(data: CacheModel): Observable<Long> {
        return Observable.create {
            it.onNext(cacheStore.put(data))
            it.onComplete()
        }
    }

    fun putAll(data: List<CacheModel>): Observable<Long> {
        return Observable.create {
            it.onNext(cacheStore.putAll(data))
            it.onComplete()
        }
    }


}