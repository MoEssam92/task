package com.mo.essam.testtask.api;


import com.mo.essam.testtask.api.model.CountriesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("all?fields=name")
    Call<List<CountriesModel>> callgetCountries();

}
