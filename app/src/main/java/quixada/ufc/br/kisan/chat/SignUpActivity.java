package quixada.ufc.br.kisan.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import quixada.ufc.br.kisan.R;


public class SignUpActivity extends ActionBarActivity {

    private static final String TAG = "SignUpActivity";
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    Button buttonSignUp;
    Button buttonLogin;
    String regId;
    String signUpUser;
    AsyncTask<Void, Void, String> sendTask;
    AtomicInteger ccsMsgId = new AtomicInteger();
    GoogleCloudMessaging gcm;
    Context context;
    private boolean signupFlag = false;
    MessageSender messageSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        context = getApplicationContext();
        buttonSignUp = (Button) findViewById(R.id.ButtonSignUp);
        messageSender = new MessageSender();
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //step 1: register with Google GCM server
                if (TextUtils.isEmpty(regId)) {
                    regId = registerGCM();
                    Log.d(TAG, "GCM RegId: " + regId);
                }

                //step 2: register with XMPP App Server
                if(!regId.isEmpty()) {
                    EditText mUserName = (EditText) findViewById(R.id.userName);
                    signUpUser = mUserName.getText().toString();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("ACTION", "SIGNUP");
                    dataBundle.putString("USER_NAME", signUpUser);
                    messageSender.sendMessage(dataBundle,gcm);
                    signupFlag = true;
                    Toast.makeText(context,
                            "Sign Up Complete!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            "Google GCM RegId Not Available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonLogin = (Button) findViewById(R.id.ButtonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {



                    //step 0: register with Google GCM server
                    if (TextUtils.isEmpty(regId)) {
                        regId = registerGCM();
                        Log.d(TAG, "GCM RegId: " + regId);
                    }

                    //step 1: user authentication

                    //step 2: get user list
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("ACTION", "USERLIST");
                    dataBundle.putString("USER_NAME", signUpUser);
                    messageSender.sendMessage(dataBundle,gcm);

                    Intent i = new Intent(context,
                            UserListActivity.class);
                                Log.d(TAG,
                            "onClick of login: Before starting userlist activity.");
                    startActivity(i);
                    finish();
                    Log.d(TAG, "onClick of Login: After finish.");

                }

        });

    }

    public String registerGCM() {

        gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId();

        if (TextUtils.isEmpty(regId)) {

            registerInBackground();

            Log.d(TAG,
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            Log.d(TAG,
                    "Regid already available: "
                            + regId
            );
        }
        return regId;
    }

    private String getRegistrationId() {
        final SharedPreferences prefs = getSharedPreferences(
                SignUpActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private int getAppVersion() {
        try {
            PackageInfo packageInfo;
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;
                    storeRegistrationId(regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d(TAG, "Error: " + msg);
                }
                Log.d(TAG, "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d(TAG, "Registered with GCM Server." + msg);
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                SignUpActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion();
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
