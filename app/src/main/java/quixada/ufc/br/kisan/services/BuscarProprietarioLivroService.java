package quixada.ufc.br.kisan.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import quixada.ufc.br.kisan.util.CaminhosWebService;
import quixada.ufc.br.kisan.model.Livro;

/**
 * Created by andersonuchoa on 13/02/16.
 */
public class BuscarProprietarioLivroService extends Service {

    private static final String TAG = "BuscarProprietarioLivroService";
    private static final String BUSCAR_PROPRIETARIO_LIVRO = "buscar-proproetario-livro";


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


        final long idLivro = (long) intent.getSerializableExtra("idLivro");

        new Thread(new Runnable() {
            @Override
            public void run() {

                int result = -1;
                final WebHelper http = new WebHelper();
               Livro livro = null;

                String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/livros/"+idLivro;


                try {
                    final WebResult webResult = http.executeHTTP(url, "GET", null);

                    if (webResult.getHttpCode() == 200) {
                        final Gson parser = new Gson();
                        livro = parser.fromJson(webResult.getHttpBody(),Livro.class);

                    }
                    Intent sendBack = new Intent("BuscarProprietarioLivro");
                    sendBack.putExtra("function",  BUSCAR_PROPRIETARIO_LIVRO);
                    sendBack.putExtra("result", result);
                    sendBack.putExtra("data", livro.getUsuario());
                    LocalBroadcastManager.getInstance(BuscarProprietarioLivroService.this).sendBroadcast(sendBack);

                } catch (IOException e) {

                    Log.d(TAG, "Exception em buscar proprietario do livro!", e);
                }
            }
        }).start();


        return Service.START_STICKY;
    }


}
