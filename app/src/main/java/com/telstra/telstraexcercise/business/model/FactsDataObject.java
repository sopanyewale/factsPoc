package com.telstra.telstraexcercise.business.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

/**
 * Created by Sopan on 13-02-18.
 */

@Generated("org.jsonschema2pojo")
public class FactsDataObject {
    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("rows")
    @Expose
    public ArrayList<FactsItem> factsItems = new ArrayList<FactsItem>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<FactsItem> getFactsItems() {
        return factsItems;
    }

    public void setFactsItems(ArrayList<FactsItem> factsItems) {
        this.factsItems = factsItems;
    }
}
