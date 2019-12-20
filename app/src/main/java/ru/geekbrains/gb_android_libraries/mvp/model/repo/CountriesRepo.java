package ru.geekbrains.gb_android_libraries.mvp.model.repo;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.gb_android_libraries.mvp.model.api.StubDataSource;
import ru.geekbrains.gb_android_libraries.mvp.model.entity.Country;

public class CountriesRepo {

    private StubDataSource dataSource;

    public CountriesRepo() {
        this.dataSource = new StubDataSource();
    }

    public Single<List<Country>> getCountries() {
        return Single.fromCallable(() -> dataSource.loadCountries()).subscribeOn(Schedulers.io());
    }
}
