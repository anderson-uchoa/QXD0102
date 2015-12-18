package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import quixada.ufc.br.kisan.R;

public class VisualizarAnuncioActivity extends AppCompatActivity {

    private ImageButton btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_anuncios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnChat = (ImageButton) findViewById(R.id.btn_chat);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMsg();
            }
        });
    }

    private void enviarMsg() {

        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);


    }

}
