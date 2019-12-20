package ru.geekbrains.gb_android_libraries.mvp.model.repo;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.gb_android_libraries.mvp.model.api.ApiHolder;
import ru.geekbrains.gb_android_libraries.mvp.model.entity.RepositoryList;

public class UserRepos {
    public Single<List<RepositoryList>> getUserRepo(String url) {
        return ApiHolder.getApi().getUserRepo(url).subscribeOn(Schedulers.io());
    }

}
