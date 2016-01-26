package quixada.ufc.br.kisan.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;

/**
 * Created by andersonuchoa on 22/01/16.
 */
public class RestServiceUsuario extends IntentService{


    private static final String TAG = "RestServiceUsuario";

    public static final String SERVICE_NAME ="REST-KISAN";

    public static final String ADICIONAR_USUARIO= "adicionar-usuario";

    public static final String BUSCAR_USUARIO_ID= "buscar-usuario-id";

    public static final String ATUALIZAR_USUARIO= "atualizar-usuario";

    public static final String LISTAR_LIVROS_USUARIO= "listar-livros-usuario";

    public RestServiceUsuario() {
        super("RestServiceUsuario");
    }

    /**
     * Handles the intents sent to the service
     * @param intent The Intent to process
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        if (ADICIONAR_USUARIO.equals(intent.getAction())) {
            final Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
            addUsuario(usuario);

        } else if (BUSCAR_USUARIO_ID.equals(intent.getAction())) {
            final Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
            buscarUsuarioIdFacebook(usuario);

        }else if (ATUALIZAR_USUARIO.equals(intent.getAction())) {
            final Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
            atualizarUsuario(usuario);
        }

    }

    private void atualizarUsuario(Usuario usuario) {


        Log.d(TAG, "Service - Atualizar Usuario");

        final WebHelper http = new WebHelper();
        int result = -1;
        Usuario results = null;
        final Gson parser = new Gson();
        final String url = "http://10.0.2.218/KisanSERVER/usuario";
        try {
            final String body = parser.toJson(usuario, Usuario.class);
            Log.e(TAG, "enviado"+ body);
            final WebResult webResult = http.executeHTTP(url, "PUT", body);

            if(webResult.getHttpCode() == 200) {
                //Convert the json string result to Java objects

                results = parser.fromJson(webResult.getHttpBody(),Usuario.class);
                result = Activity.RESULT_OK;

                Log.i(TAG, " Entrou no atualizar Usuario :"+ results.getId());
            }
        } catch (IOException e) {
            Log.d(TAG, "Exception calling atualizar service", e);
        }

        //Send the todos back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("result", result);
        sendBack.putExtra("function", ATUALIZAR_USUARIO);

        if(results != null){
            Log.e(TAG, "enviado"+ results.getNome());
            sendBack.putExtra("data", results);
        }

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);

    }


    private void buscarUsuarioIdFacebook(Usuario usuario) {

        Log.d(TAG, "Service - Buscar Usuario id Facebook");

        final WebHelper http = new WebHelper();
        int result = -1;
        Usuario results = null;
        final String url = "http://10.0.2.2:8080/KisanSERVER/usuario/buscarIdFacebook/" + usuario.getId_facebook();
        try {
            final WebResult webResult = http.executeHTTP(url, "GET", null);

            if(webResult.getHttpCode() == 200) {
                //Convert the json string result to Java objects
                final Gson parser = new Gson();
                results = parser.fromJson(webResult.getHttpBody(),Usuario.class);
                result = Activity.RESULT_OK;

                Log.i(TAG, " Entrou no busca para enviar :"+ results.getId());
            }
        } catch (IOException e) {
            Log.d(TAG, "Exception calling buscar por id facebook service", e);
        }

        //Send the todos back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("result", result);
        sendBack.putExtra("function", BUSCAR_USUARIO_ID);

        if(results != null){
            Log.i(TAG, " Entrou no busca para enviar 2 :"+ results.getId());

            sendBack.putExtra("data", results);
        }

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);

    }

    private void addUsuario(Usuario usuario) {

        final WebHelper http = new WebHelper();
        int result = -1;
        Usuario novoUsuario = null;

        final String url = "http://10.0.2.2:8080/KisanSERVER/usuario";
        final Gson parser = new Gson();

        try {
            final String body = parser.toJson(usuario, Usuario.class);
            Log.i(TAG, body);
            final WebResult webResult = http.executeHTTP(url, "POST", body);
            if(webResult.getHttpCode() == 200) {
                result = Activity.RESULT_OK;

                //Convert the string result to Java objects
                novoUsuario = parser.fromJson(webResult.getHttpBody(), Usuario.class);
                usuario.setId(novoUsuario.getId());
            }

        } catch (IOException e) {
            Log.d(RestServiceUsuario.TAG, "Exception calling add service", e);
        }

        //Send the result back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("function", ADICIONAR_USUARIO);
        sendBack.putExtra("result", result);
        if(novoUsuario != null){
            sendBack.putExtra("data", usuario);
        }

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);
    }


}
