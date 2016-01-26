package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Usuario;
;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class PreProcessamentoActivity extends AppCompatActivity {

    private static final String TAG = "PreProcessamentoActivity";
    private Usuario usuario1;
    String url = "http://10.0.2.2:8080/KisanSERVER/usuario/buscarIdFacebook/";


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

                            CustomApplication customApplication = (CustomApplication) getApplicationContext();
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
                        CustomApplication customApplication = (CustomApplication) getApplicationContext();
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

            final String url = "http://10.0.2.2:8080/KisanSERVER/usuario";
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

                    CustomApplication customApplication =(CustomApplication) getApplicationContext();
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


    }





