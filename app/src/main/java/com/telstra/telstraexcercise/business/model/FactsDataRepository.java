package com.telstra.telstraexcercise.business.model;

import javax.inject.Singleton;

/**
 * Created by Sopan on 13-02-18.
 */

@Singleton
public class FactsDataRepository {
    private FactsDataObject factsDataObject;

    public FactsDataObject getFactsDataObject() {
        return factsDataObject;
    }

    public void setFactsDataObject(FactsDataObject factsDataObject) {
        this.factsDataObject = factsDataObject;
    }
}
