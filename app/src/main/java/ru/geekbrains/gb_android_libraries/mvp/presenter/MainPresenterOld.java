//package ru.geekbrains.gb_android_libraries.mvp.presenter;
//
//import android.annotation.SuppressLint;
//
//import com.arellomobile.mvp.InjectViewState;
//import com.arellomobile.mvp.MvpPresenter;
//
//import io.reactivex.Scheduler;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.CompositeDisposable;
//import ru.geekbrains.gb_android_libraries.mvp.model.Model;
//import ru.geekbrains.gb_android_libraries.mvp.model.RxEventBus;
//import ru.geekbrains.gb_android_libraries.mvp.model.RxStuff;
//import ru.geekbrains.gb_android_libraries.mvp.model.SomeEvent;
//import ru.geekbrains.gb_android_libraries.mvp.view.MainView;
//import timber.log.Timber;
//
//@InjectViewState
//public class MainPresenterOld extends MvpPresenter<MainView> {
//    Scheduler scheduler;
//    private Model model;
//
//    public MainPresenterOld(Scheduler scheduler) {
//        this.scheduler = scheduler;
//        this.model = new Model();
//    }
//
//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//
//    @SuppressLint("CheckResult")
//    @Override
//    protected void onFirstViewAttach() {
//        super.onFirstViewAttach();
//        new RxStuff().run();
//
//        RxEventBus bus = new RxEventBus();
//        bus.observable(SomeEvent.class).subscribe(event -> Timber.d(event.getValue()));
//        bus.post(new SomeEvent("some value"));
//    }
//
//
//    public void buttonOneClick() {
//        compositeDisposable.add(model.calcValue(0).observeOn(AndroidSchedulers.mainThread()).subscribe(number->{
//            getViewState().setOneValue(number);
//        }));
//    }
//
//    public void buttonTwoClick() {
//        compositeDisposable.add(model.calcValue(1).observeOn(AndroidSchedulers.mainThread()).subscribe(number->{
//            getViewState().setOneValue(number);
//        }));
//    }
//
//    public void buttonThreeClick() {
//        compositeDisposable.add(model.calcValue(3).observeOn(AndroidSchedulers.mainThread()).subscribe(number->{
//            getViewState().setOneValue(number);
//        }));
//    }
//
//    public void textChanged(String text) {
//        getViewState().setText(text);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        compositeDisposable.dispose();
//    }
//}
