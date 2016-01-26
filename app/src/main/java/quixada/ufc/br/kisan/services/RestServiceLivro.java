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


public class RestServiceLivro extends IntentService {


    private static final String TAG = "RestServiceLivro";

    public static final String SERVICE_NAME ="REST-KISAN";

    public static final String ADICIONAR_LIVRO = "adicionar-livro";
    public static final String REMOVER_LIVRO = "remover-livro";
    public static final String ATUALIZAR_LIVRO = "atualizar-livro";

    public RestServiceLivro() {
        super("RestServiceLivro");
    }

    /**
     * Handles the intents sent to the service
     * @param intent The Intent to process
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        if (ADICIONAR_LIVRO.equals(intent.getAction())) {
            final Livro livro = (Livro) intent.getSerializableExtra("livro");
            adicionarLivro(livro);

        } else if (REMOVER_LIVRO.equals(intent.getAction())) {
            final long id = intent.getLongExtra("id", -1);
            final int posicao = intent.getIntExtra("posicao", -1);
            removerLivro(id, posicao);

        }
    }



    private void removerLivro(long id, int posicao) {
        Log.d(TAG, "Service - Remover Livro called");

        final WebHelper http = new WebHelper();
        int result = -1;
        final String url = "http://10.0.2.2:8080/KisanSERVER/livros" + id;
        try {
            final WebResult webResult = http.executeHTTP(url, "DELETE", null);
            if(webResult.getHttpCode() == 204) {
                result = Activity.RESULT_OK;
            }
        } catch (IOException e) {
            Log.d(TAG, "Exception calling delete service", e);
        }

        //Send the data back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("result", result);
        sendBack.putExtra("function", REMOVER_LIVRO);
        sendBack.putExtra("position", posicao);

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);


    }


    private void adicionarLivro(Livro livro) {
        Log.d(RestServiceLivro.TAG, "Service - Add Livro called");

        final WebHelper http = new WebHelper();
        int result = -1;
        Livro newId = null;

        final String url = "http://10.0.2.2:8080/KisanSERVER/livros";
        final Gson parser = new Gson();

        try {

            final String body = parser.toJson(livro, Livro.class);
            System.out.println(" livro:" +body);
            final WebResult webResult = http.executeHTTP(url, "POST", body);
            if(webResult.getHttpCode() == 200) {
                result = Activity.RESULT_OK;

                //Convert the string result to Java objects

                newId = parser.fromJson(webResult.getHttpBody(), Livro.class);
                livro.setId(newId.getId());
            }

        } catch (IOException e) {
            Log.d(RestServiceLivro.TAG, "Exception calling add service", e);
        }

        //Send the result back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("function",  ADICIONAR_LIVRO);
        sendBack.putExtra("result", result);
        if(newId != null){
            sendBack.putExtra("data", livro);
        }

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);
    }

}
