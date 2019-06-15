package com.mo.essam.testtask.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.essam.testtask.R;
import com.mo.essam.testtask.api.ApiInterface;
import com.mo.essam.testtask.api.RetrofitModel;
import com.mo.essam.testtask.api.model.CountriesModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesActivity extends AppCompatActivity {
    ImageView backImage;
    TextView titleText;
    ProgressBar countriesProgress;
    EditText searchEdit;

    ListView countriesList;
    ArrayList<String> countries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        initializeViews();

        getCountries();
    }

    private void getCountries() {
        ApiInterface apiInterface = RetrofitModel.getClient().create(ApiInterface.class);
        Call<List<CountriesModel>> countriesCall = apiInterface.callgetCountries();
        countriesCall.enqueue(new Callback<List<CountriesModel>>() {
            @Override
            public void onResponse(Call<List<CountriesModel>> call, Response<List<CountriesModel>> response) {
                countriesProgress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<CountriesModel> data = response.body();
                    for (CountriesModel countriesModel : data) {
                        countries.add(countriesModel.getName());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CountriesActivity.this,
                            android.R.layout.simple_expandable_list_item_1,
                            countries);
                    searchEdit.setVisibility(View.VISIBLE);
                    countriesList.setAdapter(arrayAdapter);

                } else {
                    Toast.makeText(CountriesActivity.this, getString(R.string.api_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CountriesModel>> call, Throwable t) {
                countriesProgress.setVisibility(View.GONE);
                Toast.makeText(CountriesActivity.this, getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                Log.e("api error", t.getMessage());
            }
        });
    }

    private void initializeViews() {
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleText);
        countriesProgress = findViewById(R.id.countriesProgress);
        countriesList = findViewById(R.id.countriesList);
        searchEdit = findViewById(R.id.searchEdit);

        countriesList.setTextFilterEnabled(true);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countriesList.setFilterText(searchEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
