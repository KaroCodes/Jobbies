package com.androidcamp.jobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class AuthenticationActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 19;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Facebook login
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_authentication);
        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                TextView tv = (TextView) findViewById(R.id.tv1);
                tv.setText(loginResult.toString() + ", atok: " + loginResult.getAccessToken().toString() + ", dperm: " + loginResult.getRecentlyDeniedPermissions().toString() + ",gperm: " + loginResult.getRecentlyGrantedPermissions().toString());
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        //Google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("MSG", "handleSignInResult: " + result.isSuccess());
        TextView tv = (TextView) findViewById(R.id.tv1);
        if(result.isSuccess()) {
            tv.setText(":)");
        }
        else {
            tv.setText(":(");
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        Log.d("ERR", result.toString());
    }
}
