package ru.geekbrains.gb_android_libraries.mvp.model.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import ru.geekbrains.gb_android_libraries.mvp.model.entity.RepositoryList;
import ru.geekbrains.gb_android_libraries.mvp.model.entity.User;

public interface IDataSource {
    @GET("/users/{user}")
    Single<User> getUser(@Path("user") String username);

    @GET
    Single<List<RepositoryList>> getUserRepo(@Url String url);
}
