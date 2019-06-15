package com.mo.essam.testtask.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;
import com.jaychang.sa.facebook.SimpleAuth;
import com.mo.essam.testtask.R;
import com.mo.essam.testtask.helper.PrefManager;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    CheckBox rememberCheck;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        prefManager = new PrefManager(this);
    }
    public void fbLogin(View view) {
        connectFacebook();

    }

    void connectFacebook() {
        List<String> scopes = Arrays.asList("public_profile, email");

        SimpleAuth.connectFacebook(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {
                Log.e("id", "userId:" + socialUser.userId);
                Log.e("email", "email:" + socialUser.email);
                Log.e("token", "accessToken:" + socialUser.accessToken);
                Log.e("image", "profilePictureUrl:" + socialUser.profilePictureUrl);
                Log.e("username", "username:" + socialUser.username);
                Log.e("name", "fullName:" + socialUser.fullName);
                Log.e("page", "pageLink:" + socialUser.pageLink);



                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onError(Throwable error) {
                Log.d("er", error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.d("er", "Canceled");
            }
        });
    }
    private void initializeViews(){
        rememberCheck = findViewById(R.id.rememberCheck);
        rememberCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefManager.setIsLoggedIn(isChecked);
            }
        });
    }
}
