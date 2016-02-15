package quixada.ufc.br.kisan.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.util.CaminhosWebService;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;

/**
 * Created by andersonuchoa on 27/01/16.
 */
public class VisualizarMeusLivrosService extends Service {

    private static final String TAG = "VisualizarMeusLivrosService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        CustomApplication  customApplication = (CustomApplication) getApplicationContext();

        final Usuario usuario = customApplication.getUsuario();

        new Thread(new Runnable() {
            @Override
            public void run() {

                int result = -1;
                final WebHelper http = new WebHelper();
                ArrayList<Livro> livros = null;

                String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/usuario/livros/";


                String url_m = url + usuario.getId();

                try {
                    final WebResult webResult = http.executeHTTP(url_m, "GET", null);

                    if (webResult.getHttpCode() == 200) {

                        final Gson parser = new Gson();
                        livros = parser.fromJson(webResult.getHttpBody(), new TypeToken<ArrayList<Livro>>() {
                        }.getType());
                        Log.i(TAG, livros.toString());

                    }

                    Intent sendBack = new Intent("VisualizarMeusLivros");
                    sendBack.putExtra("result", result);
                    sendBack.putExtra("data", livros);
                    LocalBroadcastManager.getInstance(VisualizarMeusLivrosService.this).sendBroadcast(sendBack);

                } catch (IOException e) {

                    Log.d(TAG, "Exception em Buscar livros do usuario!", e);
                }
            }
        }).start();


        return Service.START_STICKY;
    }

}
