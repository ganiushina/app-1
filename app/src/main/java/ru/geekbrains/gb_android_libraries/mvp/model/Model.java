package ru.geekbrains.gb_android_libraries.mvp.model;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class Model {
    private List<Integer> counters;

    public Model() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Observable<Integer> getAt(int pos) {
        return Observable.fromCallable(() -> counters.get(pos));
    }

    public Completable setAt(int pos, int value) {
        return Completable.fromAction(() -> {
            counters.set(pos, value);
        });
    }

    @SuppressLint("CheckResult")
    public Observable<Integer> calcValue(int index) {
        return Observable.create(emitter -> {
            try {
                getAt(index).subscribe(number -> {
                    Integer result = number + 1;
                    setAt(index, result).subscribe(() -> {
                        emitter.onNext(result);
                    }, e -> {
                        emitter.onError(e);
                    });
                });
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
    }
}
