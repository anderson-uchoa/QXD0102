package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.adapter.ConversasAdapter;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.dataBase.Conversa;
import quixada.ufc.br.kisan.dataBase.MensagemRealm;

public class ConversasActivity extends AppCompatActivity {


    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CustomApplication customApplication = (CustomApplication) getApplication();


        MensagemRealm mensagemRealm = new MensagemRealm(this);
        List<Conversa> conversas = mensagemRealm.buscar(customApplication.getUsuario().getId());

        String [] nomes = new String[conversas.size()];
        int i = 0;
        for (Conversa con: conversas){
            if(con.getIdUsuario1()==customApplication.getUsuario().getId()){
                nomes[i] = con.getNome1();
            }else{
                nomes[i] = con.getNome2();
            }

        }

        ConversasAdapter conversasAdapter = new ConversasAdapter(this, nomes);
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(conversasAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                //startActivity(intent);
            }
        });



    }
}
