package com.telstra.telstraexcercise.business.api;

import android.util.Log;

import com.telstra.telstraexcercise.business.model.FactsDataObject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Sopan on 14-02-18.
 */

public class ConsumeService {
    private FactsApi factsApi;

    public ConsumeService(FactsApi factsApi) {
        this.factsApi = factsApi;
    }

    public Subscription getFactList(final GetFactListCallback callback) {

        return factsApi.getFactsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends FactsDataObject>>() {
                    @Override
                    public Observable<? extends FactsDataObject> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<FactsDataObject>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ConsumeService", "Inside onError - " + e.getCause());
                        callback.onError();
                    }

                    @Override
                    public void onNext(FactsDataObject factsDataObject) {
                        callback.onSuccess(factsDataObject);
                    }
                });
    }

    public interface GetFactListCallback {
        void onSuccess(FactsDataObject factsDataObject);

        void onError();
    }
}
