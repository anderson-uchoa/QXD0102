package quixada.ufc.br.kisan.services;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import quixada.ufc.br.kisan.util.CaminhosWebService;
import quixada.ufc.br.kisan.model.Usuario;

/**
 * Created by andersonuchoa on 07/02/16.
 */
public class AddWishListService extends Service {
    private static final String TAG = "AddWishListService";



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

        final Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        Log.e(TAG, "enviado1: " + usuario);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final WebHelper http = new WebHelper();
                int result = -1;
                Usuario results = null;
                final Gson parser = new Gson();

                final String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/usuario";

                try {
                    final String body = parser.toJson(usuario, Usuario.class);
                    Log.e(TAG, "enviado"+ body);
                    final WebResult webResult = http.executeHTTP(url, "PUT", body);

                    if(webResult.getHttpCode() == 200) {

                        results = parser.fromJson(webResult.getHttpBody(),Usuario.class);
                        result = Activity.RESULT_OK;
                        Log.i(TAG, " Entrou no atualizar Usuario :"+ results.getId());
                    }

                    Intent sendBack = new Intent("AtualizarPerfil");
                    sendBack.putExtra("result", result);
                    sendBack.putExtra("data", usuario);

                } catch (IOException e) {
                    Log.d(TAG, "Exception calling atualizar service", e);
                }

            }
        }).start();


        return Service.START_STICKY;
    }
}


