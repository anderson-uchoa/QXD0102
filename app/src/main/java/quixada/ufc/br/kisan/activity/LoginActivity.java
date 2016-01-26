package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import quixada.ufc.br.kisan.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private LoginButton loginbutton;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       final Intent intent = new Intent(getApplicationContext(),PreProcessamentoActivity.class);

        

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if( accessToken != null && !accessToken.isExpired()){
                startActivity(intent);
                finish();
            }

        loginbutton = (LoginButton) findViewById(R.id.login_button);
        loginbutton.setReadPermissions(Arrays.asList("public_profile", "email", "user_location"));



        loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG,"onSuccess()");
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancel() {
                Log.w(TAG," onCancel()");

            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG,exception.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    }



