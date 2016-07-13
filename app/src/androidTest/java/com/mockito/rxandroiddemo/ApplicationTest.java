package com.mockito.rxandroiddemo;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private Observer<String> observer;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };
    }

    public void testRxJava(){
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onNext("!");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);
    }

    public void testRxJavaJust(){
        Observable<String> observable = Observable.just("Hello", "World", "!");
        observable.subscribe(observer);
    }

    public void testRxJavaFrom(){
        String[] array = {"Hello", "World", "!"};
        Observable<String> observable = Observable.from(array);
        observable.subscribe(observer);
    }

    public void testRxJavaIterable(){
        List<String> array = new ArrayList<>();
        array.add("Hello");
        array.add("World");
        array.add("!");
        Observable<String> observable = Observable.from(array);
        observable.subscribe(observer);
    }

    public void testRxJavaIntegerToString(){
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<String> stringObservable = observable.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        });
        stringObservable.subscribe(observer);
    }

    public void testRxJavaIntegerToStringWithAction(){
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<String> stringObservable = observable.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        });
        stringObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    /*Java 8 Lambda表达式 */
    public void testRxJavaIntegerToStringWithLambda(){
//        Observable.just(1,2,3)
//                .map(integer -> integer.toString())
//                .subscribe(System.out::print);
    }


}