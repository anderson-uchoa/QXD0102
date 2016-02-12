package quixada.ufc.br.kisan.application;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import quixada.ufc.br.kisan.model.Usuario;

/**
 * Created by andersonuchoa on 24/01/16.
 */
public class CustomApplication extends Application {

    private Usuario usuario;
    private String ip;

public  CustomApplication (){
    this.ip = "192.168.1.4:8080";
}


    @Override
    public void onCreate() {
        super.onCreate();
       usuario = new Usuario();

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("onLowMemory()", "");
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("onLowMemory()", "onLowMemory(" + level + ")");
        Log.i("onLowMemory()" , "TRIM_MEMORY_BACKGROUND:"+TRIM_MEMORY_BACKGROUND );
        Log.i("onLowMemory()" , "TRIM_MEMORY_COMPLETE:"+TRIM_MEMORY_COMPLETE );
        Log.i("onLowMemory()" , "TRIM_MEMORY_MODERATE:"+TRIM_MEMORY_MODERATE );
        Log.i("onLowMemory()" , "TRIM_MEMORY_RUNNING_CRITICAL:"+TRIM_MEMORY_RUNNING_CRITICAL  );
        Log.i("onLowMemory()" , "TRIM_MEMORY_RUNNING_LOW:"+TRIM_MEMORY_RUNNING_LOW );
        Log.i("onLowMemory()" , "TRIM_MEMORY_UI_HIDDEN:"+TRIM_MEMORY_UI_HIDDEN );

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
