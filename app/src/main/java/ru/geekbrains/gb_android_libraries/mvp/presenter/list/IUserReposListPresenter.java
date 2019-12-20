package ru.geekbrains.gb_android_libraries.mvp.presenter.list;

import io.reactivex.subjects.PublishSubject;
import ru.geekbrains.gb_android_libraries.mvp.view.list.CountryRowView;
import ru.geekbrains.gb_android_libraries.mvp.view.list.ReposRowView;

public interface IUserReposListPresenter {
    void bind(ReposRowView view);
    int getCount();
    PublishSubject<ReposRowView> getClickSubject();
}
