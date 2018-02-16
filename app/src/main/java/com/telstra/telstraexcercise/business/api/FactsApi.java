package com.telstra.telstraexcercise.business.api;

import com.telstra.telstraexcercise.business.model.FactsDataObject;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Sopan on 13-02-18.
 */

public interface FactsApi {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<FactsDataObject> getFactsData();
}
