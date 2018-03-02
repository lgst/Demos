package com.jiainfo.demos.rx_retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiainfo.demos.R;
import com.jiainfo.demos.bean.Repo;
import com.jiainfo.demos.retrofit.RetrofitActivity;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxJavaRetrofitActivity extends AppCompatActivity {

    private TextView mTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_retrofit);
        initView();
    }

    public void showClick(View view) {
        mTvText.setText(null);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        final RetrofitActivity.GitHubService service = retrofit.create(RetrofitActivity.GitHubService.class);
        service.listReposRx("lgst")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<Repo>>() {
                    @Override
                    public void call(List<Repo> repos) {
                        for (Repo repo :repos) {
                            mTvText.append(repo.getName()+"\n");
                        }
                    }
                });
    }

    private void initView() {
        mTvText = (TextView) findViewById(R.id.tv_text);
    }
}
