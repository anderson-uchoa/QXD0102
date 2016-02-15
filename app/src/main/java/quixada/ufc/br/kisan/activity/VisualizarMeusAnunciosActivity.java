package quixada.ufc.br.kisan.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.Util.CaminhosWebService;
import quixada.ufc.br.kisan.adapter.ExpandableListMeusLivrosAdapter;
import quixada.ufc.br.kisan.adapter.OnCustomClickListener;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.VisualizarMeusLivrosService;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class VisualizarMeusAnunciosActivity extends AppCompatActivity implements OnCustomClickListener {

    private static final String TAG = "VisualizarMeusAnunciosActivity";

    private ArrayList<Livro> meusLivros = new ArrayList<Livro>();
    private ExpandableListMeusLivrosAdapter meusLivrosAdapter;
    private ExpandableListView expandableListView;

    String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/livros/";

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_meus_anuncios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        expandableListView = (ExpandableListView) findViewById(R.id.expandableViewMeusAnuncios);

        meusLivrosAdapter = new ExpandableListMeusLivrosAdapter(this, meusLivros,this);


        Intent intent = new Intent(VisualizarMeusAnunciosActivity.this, VisualizarMeusLivrosService.class);
        startService(intent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getBaseContext(), AddAnuncioActivity.class);
                startActivityForResult(intent, 2);
            }
        });


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                final int serviceResult = intent.getIntExtra("result", -1);

                if (serviceResult == RESULT_OK) {
                    ArrayList<Livro> livros = (ArrayList<Livro>) intent.getSerializableExtra("data");
                        Log.i(TAG, livros.toString());

                    for (Livro livro : livros) {
                        meusLivros.add(livro);
                        expandableListView.setAdapter(meusLivrosAdapter);
                    }


                    if (livros == null) {
                        Toast.makeText(VisualizarMeusAnunciosActivity.this, "Você não possui livros adicionados!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(VisualizarMeusAnunciosActivity.this, "Sua lista de livros!", Toast.LENGTH_SHORT).show();
                    }
 }
            }




        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 ){
            if(resultCode == 1){
              meusLivrosAdapter.addNovoLivro((Livro) data.getSerializableExtra("livro"));
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("VisualizarMeusLivros"));

    }

    @Override
    public void OnCustomClick(View view, Livro livro) {



    }

    @Override
    public void OnCustomClick(View view, Long position) {

        new removerLivro().execute(position);
    }

    @Override
    public void OnCustomClick(View view, Usuario usuario) {

    }


    private class removerLivro extends AsyncTask <Long, Void, String>{

        final WebHelper http = new WebHelper();

        @Override
        protected String doInBackground(Long... urls) {
           Long id = urls[0];
            int result = -1;
           String url_m = url + id;
            try {

                final WebResult webResult = http.executeHTTP(url_m, "DELETE", null);
                if(webResult.getHttpCode() == 204) {
                    result = Activity.RESULT_OK;
                }
            } catch (IOException e) {
                Log.d(TAG, "Exception calling delete service", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
