package quixada.ufc.br.kisan.activity;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.adapter.MeusLivrosAdapter;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class VisualizarMeusAnunciosActivity extends AppCompatActivity   {

    private static final String TAG = "VisualizarMeusAnunciosActivity";
    String url = "http://10.0.2.2:8080/KisanSERVER/usuario/livros/";

    private ArrayList<Livro> meusLivros = new ArrayList<Livro>();
    private ListView listView;
    private MeusLivrosAdapter meusLivrosAdapter;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_meus_anuncios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CustomApplication customApplication = (CustomApplication) getApplicationContext();
        Usuario usuario = customApplication.getUsuario();

        Log.i(TAG, "usuario:"+ usuario.getId());
        new ListarMeusLivros().execute(usuario.getId());

        listView = (ListView) findViewById(R.id.listViewMeusAnuncios);

         meusLivrosAdapter = new MeusLivrosAdapter(this,  meusLivros);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), AddAnuncioActivity.class);
                startActivity(intent);
            }
        });


    }


    private class ListarMeusLivros extends AsyncTask<Long, Void, String>{

        final WebHelper http = new WebHelper();
        ArrayList<Livro> livros = null;

        protected String doInBackground(Long... urls) {

            Long id = urls[0];

            String url_m = url + id;

            try {
                final WebResult webResult = http.executeHTTP(url_m, "GET", null);

                if (webResult.getHttpCode() == 200) {

                    final Gson parser = new Gson();
                    livros = parser.fromJson(webResult.getHttpBody(),  new TypeToken<ArrayList<Livro>>(){}.getType());

                    Log.i(TAG, "livros do usuario:"+ livros.toString());



                }
            } catch (IOException e) {

                Log.d(TAG, "Exception em buscar livros do usuario!", e);
            }
            return null;

            }

        @Override
        protected void onPostExecute(String s) {

          for( Livro livro : livros){
              meusLivros.add(livro);
              listView.setAdapter(meusLivrosAdapter);
          }

            if (livros == null){

                Toast.makeText(VisualizarMeusAnunciosActivity.this, "Você não possui livros anunciados!", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(VisualizarMeusAnunciosActivity.this, "Sua lista de livros!", Toast.LENGTH_SHORT).show();

            }
             super.onPostExecute(s);
        }
    }
}

/*
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.item_deletar:
                Toast.makeText(this, "Deletar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_Editar:
                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show();
                intent = new Intent(getBaseContext(), EditAnuncioActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_Visualizar:
                Toast.makeText(this, "Visualizar", Toast.LENGTH_SHORT).show();
                intent = new Intent(getBaseContext(), VisualizarAnuncioActivity.class);
                startActivity(intent);

                return true;

        }

    return  true;
    }*/



