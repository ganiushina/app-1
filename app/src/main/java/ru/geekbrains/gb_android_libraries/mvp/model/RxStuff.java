package ru.geekbrains.gb_android_libraries.mvp.model;

import android.text.TextUtils;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RxStuff {



    private Observer<String> getStringObserver() {
        return new Observer<String>() {
            Disposable subscription;

            @Override
            public void onSubscribe(Disposable d) {
                subscription = d;
                Timber.d("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Timber.d("onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("onError: " + e);
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete");
            }
        };
    }

    private Observer<Long> getLongObserver() {
        return new Observer<Long>() {
            Disposable subscription;

            @Override
            public void onSubscribe(Disposable d) {
                subscription = d;
                Timber.d("onSubscribe");
            }

            @Override
            public void onNext(Long s) {
                Timber.d("onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("onError: " + e);
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete");
            }
        };
    }

    private Observer<List<String>> getListStringObserver() {
        return new Observer<List<String>>() {
            Disposable subscription;

            @Override
            public void onSubscribe(Disposable d) {
                subscription = d;
                Timber.d("onSubscribe");
            }

            @Override
            public void onNext(List<String> l) {
                Timber.d("onNext: " + TextUtils.join(",", l));
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("onError: " + e);
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete");
            }
        };
    }



    public void run() {
//        just();
//        fromIterable();
//        interval();
//        take();
//        skip();
//        map();
//        distinct();
//        filter();
//        merge();
//        zip();
//        flatMap();
//        flatMapVsSwitchMap();
//        flowable();
        hotCold();
    }

    public void just() {
        Observable<String> justObservable = Observable
                .just("just0", "just1", "just2");

        justObservable.subscribe(getStringObserver());
    }

    public void fromIterable() {
        Observable<String> fromIterableObservable = Observable
                .fromIterable(Arrays.asList("from0", "from1", "from2", "from3"));

        fromIterableObservable.subscribe(getStringObserver());
    }

    public void interval() {
        Observable<Long> fromIterableObservable = Observable
                .interval(1, TimeUnit.SECONDS);

        fromIterableObservable.subscribe(getLongObserver());
    }

    public void take() {
        Observable<String> takeObservable = Observable
                .fromIterable(Arrays.asList("take0", "take1", "take2", "take3"))
                .take(2);

        takeObservable.subscribe(getStringObserver());
    }


    public void skip() {
        Observable<String> skipObservable = Observable
                .fromIterable(Arrays.asList("skip0", "skip1", "skip2", "skip3"))
                .skip(2);

        skipObservable.subscribe(getStringObserver());
    }

    public void map() {
        Observable<String> mapObservable = Observable
                .fromIterable(Arrays.asList("map0", "map1", "map2", "map3"))
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "_" + s;
                    }
                });

        mapObservable.subscribe(getStringObserver());
    }

    public void distinct() {
        Observable<String> distinctObservable = Observable
                .fromIterable(Arrays.asList("distinct", "distinct0", "distinct1", "distinct2", "distinct3"))
                .map(s -> s.contains("0") ? s : s + "0")
                .distinct();

        distinctObservable.subscribe(getStringObserver());
    }

    public void filter() {
        Observable<String> filterObservable = Observable
                .fromIterable(Arrays.asList("_filter", "filter", "filter10", "filter12", "filter2", "filter31"))
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !s.contains("1");
                    }
                });
        filterObservable.subscribe(getStringObserver());
    }

    public void merge() {
        Observable<String> mergeObservable = Observable
                .fromIterable(Arrays.asList("_merge", "merge0", "merge1", "merge2", "merge3", "merge4"))
                .skip(1)
                .mergeWith(Observable.fromIterable(Arrays.asList("_merge", "merge2", "merge3", "merge4", "merge5", "merge6")))
                .distinct();
        mergeObservable.subscribe(getStringObserver());

    }

    public void zip() {
        Observable<String> observableOne = Observable.just("zip0");
        Observable<String> observableTwo = Observable.just("zip1");
        Observable<String> observableThree = Observable.just("zip2");
        Observable<String> observableFour = Observable.just("zip3");

        Observable<List<String>> zipObservable = Observable.zip(observableOne, observableTwo, observableThree, observableFour, new Function4<String, String, String, String, List<String>>() {
            @Override
            public List<String> apply(String s, String s2, String s3, String s4) throws Exception {
                return Arrays.asList(s, s2, s3, s4);
            }
        });

        zipObservable.subscribe(getListStringObserver());
    }

    public void flatMap() {
        Observable<String> observableCOne = Observable.just("flatMap0");
        Observable<String> observableCTwo = Observable.just("flatMap1");
        Observable<String> observableCThree = Observable.just("flatMap2");
        Observable<String> observableCFour = Observable.just("flatMap3");

        Observable<String> flatMapObservable = Observable.zip(observableCOne, observableCTwo, observableCThree, observableCFour, new Function4<String, String, String, String, List<String>>() {
            @Override
            public List<String> apply(String s, String s2, String s3, String s4) throws Exception {
                return Arrays.asList(s, s2, s3, s4);
            }
        }).flatMap((Function<List<String>, ObservableSource<String>>) strings -> Observable.fromIterable(strings));

        flatMapObservable.subscribe(getStringObserver());
    }

    public void flatMapVsSwitchMap() {
        final List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f");
        final TestScheduler scheduler = new TestScheduler();

        Observable.fromIterable(items)
                .switchMap(s -> {
                    final int delay = new Random().nextInt(10);
                    return Observable.just(s + "x")
                            .delay(delay, TimeUnit.SECONDS, scheduler);
                })
                .toList()
                .doOnSuccess(list -> {
                    Timber.d("flatMap: " + list);
                })
                .subscribe();

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
    }


    void flowable(){
        Flowable.range(1, 1_000_000)
                .onBackpressureLatest()
                .observeOn(Schedulers.computation(), false, 512)
                .subscribe(num ->{
                    Timber.d(num.toString());
                }, Throwable::printStackTrace);
    }

    void hotCold(){
        Observable<Long> intervalCold = Observable.interval(1, TimeUnit.SECONDS).doOnNext(l -> Timber.d("Emit: " + l));
//
//        intervalCold.subscribe(l -> {
//            Timber.d("onNext1: " + l);
//        });
//
//        intervalCold.subscribe(l -> {
//            Timber.d("onNext2: " + l);
//        });
//
//        intervalCold.subscribe(l -> {
//            Timber.d("onNext3: " + l);
//        });


        ConnectableObservable<Long> intervalHot = Observable.interval(1, TimeUnit.SECONDS).doOnNext(l -> Timber.d("Emit: " + l)).publish();

        intervalHot.subscribe(l -> {
            Timber.d("onNext1: " + l);
        });

        intervalHot.subscribe(l -> {
            Timber.d("onNext2: " + l);
        });

        Disposable d = intervalHot.subscribe(l -> {
            Timber.d("onNext3: " + l);
        });

        intervalHot.connect();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        d.dispose();


        Disposable d2 = intervalHot.subscribe(l -> {
            Timber.d("onNext4: " + l);
        });

    }

    //ПЕРЕРЫВ 10 минут
}
