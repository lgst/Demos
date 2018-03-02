package com.jiainfo.demos.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiainfo.demos.R;
import com.jiainfo.demos.bean.Repo;
import com.jiainfo.demos.bean.User;
import com.jiainfo.demos.retrofit.RetrofitActivity;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author lyg
 *         RxAndroid实例
 *         RxAndroid四个基本要素Observable（被观察者）、Observer（观察者）、subscribe（订阅）、事件
 *         异步
 */
public class RxAndroidActivity extends AppCompatActivity {

    private TextView mTvText;
    private Subscriber<String> subscriber;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        initView();
        users.add(new User("Bob", 17));
        users.add(new User("Gru", 23));
        users.add(new User("Jack", 19));
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mTvText.append("completed\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvText.append("error\n");
            }

            @Override
            public void onNext(String s) {
                mTvText.append(s + "\n");
            }
        };
    }

    public void downloadClick(View view) {
        mTvText.setText(null);
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        download();
                        subscriber.onNext("download done!");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mTvText.append(s + "\n");
                    }
                });
    }

    public void uploadClick(View view) {
        mTvText.setText(null);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                upload();
                subscriber.onNext("upload done!");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void mapClick(View view) {
        mTvText.setText(null);
        Observable.from(users)
                .map(new Func1<User, String>() {
                    @Override
                    public String call(User user) {
                        download();
                        return user.getName();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mTvText.append(s+"\n");
                    }
                });
    }


    public void flatMapClick(View view) {
        mTvText.setText(null);
        String[] users = {"lgst","mcxiaoke","houjixin"};
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        final RetrofitActivity.GitHubService service = retrofit.create(RetrofitActivity.GitHubService.class);
        Observable.from(users)
                .flatMap(new Func1<String, Observable<Repo>>() {
                    @Override
                    public Observable<Repo> call(String s) {
                        List<Repo> repoList = new ArrayList<>();
                        try {
                            List<Repo> repos = service.listRepos(s).execute().body();
                            if (repos!=null){
                                repoList.addAll(repos);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.from(repoList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Repo>() {
                    @Override
                    public void call(Repo repo) {
                        mTvText.append(repo.getName()+"\n");
                    }
                });
    }

    void download() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void upload() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mTvText = findViewById(R.id.tv_text);
    }

}
