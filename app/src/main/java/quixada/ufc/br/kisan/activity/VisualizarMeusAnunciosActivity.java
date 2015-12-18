package quixada.ufc.br.kisan.activity;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;


import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.adapter.MeusAnunciosAdapter;

public class VisualizarMeusAnunciosActivity extends AppCompatActivity {


    private String livros[] = new String[]{"Dom Quixote", "Caso dos Dez Negrinhos",
            "Ela a Feiticeira", "Harry Potter e a pedra filosofal"};

    private Integer[] imgid = {
            R.raw.domquixote,
            R.raw.casodosdeznegrinhos,
            R.raw.elaafeiticeira,
            R.raw.harryoottereapedrafilosofal
    };

    private ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_meus_anuncios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


        MeusAnunciosAdapter adapter = new MeusAnunciosAdapter(this, livros, imgid);
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = livros[+position];
                Intent intent = new Intent(getBaseContext(), VisualizarAnuncioActivity .class);
                startActivity(intent);
            }
        });


    }


}