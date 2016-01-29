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

import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;

/**
 * Created by andersonuchoa on 28/01/16.
 */
public class ListarLivrosService  extends Service {
    private static final String TAG = "ListarLivrosService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                int result = -1;
                final WebHelper http = new WebHelper();
                ArrayList<Livro> livros = null;
                String url = "http://192.168.1.33:8080/KisanSERVER/livros";


                try {
                    final WebResult webResult = http.executeHTTP(url, "GET", null);

                    if (webResult.getHttpCode() == 200) {

                        final Gson parser = new Gson();
                        livros = parser.fromJson(webResult.getHttpBody(), new TypeToken<ArrayList<Livro>>() {
                        }.getType());

                    }

                    Intent sendBack = new Intent("ListarLivros");
                    sendBack.putExtra("result", result);
                    sendBack.putExtra("data", livros);
                    LocalBroadcastManager.getInstance(ListarLivrosService.this).sendBroadcast(sendBack);

                } catch (IOException e) {

                    Log.d(TAG, "Exception em Listar Livros!", e);
                }
            }
        }).start();


        return Service.START_STICKY;
    }


}