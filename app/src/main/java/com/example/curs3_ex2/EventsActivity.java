package com.example.curs3_ex2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class EventsActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        textView1 = findViewById(R.id.event_text1);
        textView2 = findViewById(R.id.event_text2);


        List<Integer> liters = Arrays.asList(1, 2, 3, 4);
        List<Integer> liters2 = Arrays.asList(5, 6, 7, 8);
        Observable<Integer> observable1 = Observable.fromIterable(liters);
        Observable<Integer> observable2 = Observable.fromIterable(liters2);


        Subject<Integer> subject = ReplaySubject.create();
        Observable.concat(observable1, observable2).subscribe(subject);
        subject.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                integer = integer + 1;
                return integer;
            }
        }).subscribe(getFirstObserver());
        subject.subscribe(getSecondObserver());
        }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {

                Log.d(TAG, " First onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                 Log.d(TAG, " First onComplete");
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                 Log.d(TAG, " Second onSubscribe : " + d.isDisposed());
                }

            @Override
            public void onNext(Integer value) {
                 Log.d(TAG, " Second onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " Second onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                 Log.d(TAG, " Second onComplete");
            }
        };
    }
}