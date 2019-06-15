package com.mo.essam.testtask.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountriesModel {
    @SerializedName("name")
    @Expose
    public String name;

    public String getName() {
        return name;
    }
}
