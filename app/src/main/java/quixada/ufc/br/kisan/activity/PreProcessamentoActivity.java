package quixada.ufc.br.kisan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.chat.Config;
import quixada.ufc.br.kisan.chat.SignUpActivity;
import quixada.ufc.br.kisan.util.CaminhosWebService;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Usuario;

import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class PreProcessamentoActivity extends AppCompatActivity {

    private static final String TAG = "PreProcessamentoActivity";
    private Usuario usuario1;
    String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/usuario/buscarIdFacebook/";
    GoogleCloudMessaging gcm;
    String regId;
    private static final String APP_VERSION = "appVersion";
    public static final String REG_ID = "regId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_processamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        Log.i(TAG, object.toString());

                        usuario1 = new Usuario();

                        try {


                            usuario1.setId_facebook(object.getLong("id"));
                            usuario1.setCidade(object.getJSONObject("location").getString("name"));
                            usuario1.setNome(object.getString("name"));
                            usuario1.setEmail(object.getString("email"));

                           if (registerGCM().isEmpty()){

                               Toast.makeText(PreProcessamentoActivity.this, "Erro no cadastro!", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(PreProcessamentoActivity.this, LoginActivity.class);
                               startActivity(intent);
                               finish();

                           }

                            usuario1.setTokenGCM(registerGCM());
                            CustomApplication customApplication = (CustomApplication) getApplication();
                            customApplication.setUsuario(usuario1);

                            Log.i(TAG, object.toString());
                            new verificarCadastrado().execute(usuario1.getId_facebook());


                            Log.i(TAG, object.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,location");
        request.setParameters(parameters);
        request.executeAsync();


    }


    private class verificarCadastrado extends AsyncTask<Long, Void, String> {

        final WebHelper http = new WebHelper();
        Usuario usuario = null;


        protected String doInBackground(Long... urls) {

            Long id = urls[0];

            String url_m = url + id;

            try {
                final WebResult webResult = http.executeHTTP(url_m, "GET", null);

                if (webResult.getHttpCode() == 200) {

                    final Gson parser = new Gson();
                    usuario = parser.fromJson(webResult.getHttpBody(), Usuario.class);


                    if (usuario == null){
                        cadastrarUsuario();
                    }else{
                        CustomApplication customApplication = (CustomApplication) getApplication();
                        customApplication.setUsuario(usuario);
                    }


                }
            } catch (IOException e) {

                Log.d(TAG, "Exception em Buscar Usuario por idFacebook!", e);
            }

            return null;
        }


        private void cadastrarUsuario() {

            final WebHelper http = new WebHelper();
            Usuario novoUsuario = null;


            final String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/usuario";
            final Gson parser = new Gson();

            try {
                final String body = parser.toJson(usuario1, Usuario.class);
                Log.i("teste", body);
                final WebResult webResult = http.executeHTTP(url, "POST", body);

                Log.i("teste", webResult.getHttpCode()+"");
                if(webResult.getHttpCode() == 200) {

                    novoUsuario = parser.fromJson(webResult.getHttpBody(), Usuario.class);
                    Log.i("teste", novoUsuario.toString());
                    usuario1.setId(novoUsuario.getId());

                    CustomApplication customApplication =(CustomApplication) getApplication();
                    customApplication.setUsuario(usuario1);

                }
            } catch (IOException e) {
                Log.d(TAG, "Exception em cadastrar novo Usuario!", e);
            }

        }

        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
                super.onPostExecute(s);
            }
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



    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(PreProcessamentoActivity.this);
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




    private int getAppVersion() {
        try {
            PackageInfo packageInfo;
            packageInfo = PreProcessamentoActivity.this.getPackageManager()
                    .getPackageInfo(PreProcessamentoActivity.this.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
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


}







