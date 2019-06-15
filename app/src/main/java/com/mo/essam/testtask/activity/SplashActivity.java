package com.mo.essam.testtask.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mo.essam.testtask.R;
import com.mo.essam.testtask.helper.PrefManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {
    ImageView logoImage;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeViews();
        prefManager = new PrefManager(this);

        printHashKey();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (prefManager.getIsLoggedIn()) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);


    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("HASH KEY:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    private void initializeViews() {
        logoImage = findViewById(R.id.logoImage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logoImage.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft).duration(1500).playOn(logoImage);
            }
        }, 10);

    }


}
