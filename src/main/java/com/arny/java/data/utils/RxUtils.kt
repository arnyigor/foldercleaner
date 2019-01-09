package com.arny.java.data.utils

import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Subscribe on function value
 * @param T function value(MUST NOT BE NULL)
 */
fun <T> subscribeOnFunctionValue(onLoadFunc: () -> T): Observable<T> {
    return Observable.create<T> { subscriber ->
        Observable.fromCallable { onLoadFunc() }
                .subscribe({ response ->
                    subscriber.onNext(response)
                }, { error ->
                    subscriber.onError(error)
                })
    }.retryWhen { errors ->
        errors.flatMap { _ ->
            return@flatMap Observable.timer(1, TimeUnit.MILLISECONDS)
        }
    }
}

fun <T> IOThreadObservable(observable: Observable<T>): Observable<T> {
    return observable.subscribeOn(Schedulers.io())
}

fun <T> IOThreadObservable(scheduler: Scheduler, observable: Observable<T>): Observable<T> {
    return observable.subscribeOn(scheduler)
}

fun <T> observeOnMainThread(observable: Observable<T>): Observable<T> {
    return observable.observeOn(Schedulers.computation())
}

fun <T> Observable<T>.observeOnMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
}


fun <T> Observable<T>.observeOnIO(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}

fun <T> Single<T>.observeOnMain(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
}

fun <T> Maybe<T>.observeOnMain(): Maybe<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
}

fun <T> mainThreadObservable(observable: Observable<T>): Observable<T> {
    return observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
}

fun <T> mainThreadObservable(observable: Single<T>): Single<T> {
    return mainThreadObservable(Schedulers.io(), observable)
}

fun <T> mainThreadObservable(scheduler: Scheduler, observable: Single<T>): Single<T> {
    return observable.subscribeOn(scheduler).observeOn(Schedulers.computation())
}

fun <T> mainThreadObservable(scheduler: Scheduler, observable: Observable<T>): Observable<T> {
    return observable.subscribeOn(scheduler).observeOn(Schedulers.computation())
}

fun <T> mainThreadObservable(flowable: Flowable<T>): Flowable<T> {
    return flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
}

fun <T> mainThreadObservable(scheduler: Scheduler, flowable: Flowable<T>): Flowable<T> {
    return flowable.subscribeOn(scheduler).observeOn(Schedulers.computation())
}

fun observeChangesString(observableSubscribe: ObservableOnSubscribe<String>, timeoutMs: Long, offsetWords: Int, onSubscribe: (String) -> Unit, onError: (Throwable) -> Unit = {}): Disposable {
    return Observable.create<String>(observableSubscribe)
            .debounce(timeoutMs, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() && it.trim().length >= offsetWords }
            .distinct()
            .observeOnMain()
            .subscribe({ onSubscribe(it) }, { onError(it) })
}
