package quixada.ufc.br.kisan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.AtualizarPerfilService;
import quixada.ufc.br.kisan.services.RestServiceUsuario;

public class VisualizarPerfilActivity extends AppCompatActivity {

    public static final String TAG = "VisualizarPerfilActivity";

    private EditText edtNome;
    private EditText edtCidade;
    private EditText edtEmail;
    private Button btnAtualizar;


    private BroadcastReceiver broadcastReceiver;
    private Usuario usuario;

    CustomApplication customApplication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = new Usuario();

        customApplication = (CustomApplication) this.getApplicationContext();

        edtCidade = (EditText) findViewById(R.id.input_cidade_perfil);
        edtEmail = (EditText) findViewById(R.id.input_email_perfil);
        edtNome = (EditText) findViewById(R.id.input_name_perfil);


        edtNome.setText(customApplication.getUsuario().getNome());
        edtEmail.setText(customApplication.getUsuario().getEmail());
        edtCidade.setText(customApplication.getUsuario().getCidade());

        btnAtualizar = (Button) findViewById(R.id.btn_Atualizar_perfil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.btnAtualizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                usuario.setEmail(edtEmail.getText().toString());
                usuario.setCidade(edtCidade.getText().toString());
                usuario.setNome(edtNome.getText().toString());
                usuario.setId(customApplication.getUsuario().getId());
                usuario.setId_facebook(customApplication.getUsuario().getId_facebook());

                Intent intent = new Intent(VisualizarPerfilActivity.this, AtualizarPerfilService.class);
                intent.putExtra("usuario", usuario);
                startService(intent);

            }
        });


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                final int serviceResult = intent.getIntExtra("result", -1);

                if (serviceResult == RESULT_OK) {

                        Usuario usuario = (Usuario) intent.getSerializableExtra("data");
                        customApplication.setUsuario(usuario);

                    Toast.makeText(VisualizarPerfilActivity.this, " Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(VisualizarPerfilActivity.this, "Perfil não atualizado!", Toast.LENGTH_SHORT).show();

                }

            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("AtualizarPerfil"));

    }



}
