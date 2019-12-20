package ru.geekbrains.gb_android_libraries.mvp.model;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxEventBus {

    private final Subject<Object> bus = PublishSubject.create();

    public RxEventBus() {
    }

    public void post(@NonNull Object event) {
        if (this.bus.hasObservers()) {
            this.bus.onNext(event);
        }
    }


    public <T> Observable<T> observable(@NonNull Class<T> eventClass) {
        return this.bus.filter(o -> o != null)
                .filter(eventClass::isInstance)
                .cast(eventClass);
    }

}
