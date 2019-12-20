package ru.geekbrains.gb_android_libraries.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.gb_android_libraries.mvp.model.entity.RepositoryList;
import ru.geekbrains.gb_android_libraries.mvp.model.repo.CountriesRepo;
import ru.geekbrains.gb_android_libraries.mvp.model.repo.UserRepos;
import ru.geekbrains.gb_android_libraries.mvp.model.repo.UsersRepo;
import ru.geekbrains.gb_android_libraries.mvp.presenter.list.IUserReposListPresenter;
import ru.geekbrains.gb_android_libraries.mvp.view.MainView;
import ru.geekbrains.gb_android_libraries.mvp.view.list.ReposRowView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    class UserReposListPresenter implements IUserReposListPresenter {

        PublishSubject<ReposRowView> clickSubject = PublishSubject.create();
        List<RepositoryList> repositoryLists = new ArrayList<>();


        @Override
        public void bind(ReposRowView view) {
            RepositoryList repositoryList = repositoryLists.get(view.getPos());
            view.setTitle(repositoryList.getName());
        }

        @Override
        public int getCount() {
            return repositoryLists.size();
        }

        @Override
        public PublishSubject<ReposRowView> getClickSubject() {
            return clickSubject;
        }
    }

    private Scheduler mainThreadScheduler;
    private CountriesRepo countriesRepo;
    private UsersRepo usersRepo;
    private UserRepos userRepos;
    private UserReposListPresenter userReposListPresenter;


    private String reposUrl;

    public MainPresenter(Scheduler scheduler) {
        this.mainThreadScheduler = scheduler;
        this.countriesRepo = new CountriesRepo();
        this.usersRepo = new UsersRepo();
        this.userRepos = new UserRepos();
        this.userReposListPresenter = new UserReposListPresenter();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadUser();

        userReposListPresenter.getClickSubject().subscribe(reposRowView -> {
            RepositoryList repositoryList = userReposListPresenter.repositoryLists.get(reposRowView.getPos());
            getViewState().showMessage(repositoryList.getName());
        });
    }


    public IUserReposListPresenter getUserReposListPresenter() {
        return userReposListPresenter;
    }

    @SuppressLint("CheckResult")
    private void loadUserRepos(String url) {
        getViewState().showLoading();
        userRepos.getUserRepo(url)
                .observeOn(mainThreadScheduler)
                .subscribe(repositoryLists -> {
                    userReposListPresenter.repositoryLists.clear();
                    userReposListPresenter.repositoryLists.addAll(repositoryLists);
                    getViewState().updateList();
                    getViewState().hideLoading();

                }, t -> Timber.e(t));

    }

    @SuppressLint("CheckResult")
    private void loadUser() {
        getViewState().showLoading();
        usersRepo.getUser("googlesamples")
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {
                    this.getViewState().hideLoading();
                    this.getViewState().setUsername(user.getLogin());
                    this.getViewState().loadImage(user.getAvatarUrl());
            //        this.getViewState().loadReposUrl(user.getReposUrl());
                    this.reposUrl = user.getReposUrl();
                 //   loadUserRepos(reposUrl);
                    loadUserRepos(reposUrl);
                }, t -> {
                    getViewState().hideLoading();
                    getViewState().showMessage(t.getMessage());
                    Timber.e(t);
                });
    }

    @SuppressLint("CheckResult")
    private void loadDataWithOkHttp(){
        Single<String> single = Single.fromCallable(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/users/googlesamples")
                    .build();
            return client.newCall(request).execute().body().string();
        });

        single.subscribeOn(Schedulers.io()).subscribe(s -> {
            Timber.d(s);
        });
    }


}
