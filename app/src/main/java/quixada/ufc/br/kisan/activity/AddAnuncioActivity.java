package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.gson.Gson;



import java.io.IOException;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class AddAnuncioActivity extends AppCompatActivity {

    private CustomApplication application = new CustomApplication();
    String url = "http://"+application.getIp()+"/KisanSERVER/livros";
    private static final String TAG = "AddAnuncioActivity";


    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtGenero;
    private EditText edtAutor;
    private Button addLivro;

    private Livro livro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anuncio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtTitulo = (EditText) findViewById(R.id.input_titulo_livro);
        edtDescricao = (EditText) findViewById(R.id.input_descricao_livro);
        addLivro = (Button) findViewById(R.id.btn_Adicionar_livro);
        edtAutor = (EditText) findViewById(R.id.input_autor_livro);
        edtGenero =  (EditText) findViewById(R.id.spinner_livro);

        livro = new Livro();


        CustomApplication customApplication = (CustomApplication) getApplicationContext();
        livro.setUsuario(customApplication.getUsuario());



        addLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                livro.setTitulo(edtTitulo.getText().toString());
                livro.setSinopse(edtDescricao.getText().toString());
                livro.setGenero(edtGenero.getText().toString());
                livro.setAutor(edtAutor.getText().toString());

                new adiocionarLivro().execute(url);
            }
        });


        }

    private class adiocionarLivro extends AsyncTask<String, Void, String> {

        final WebHelper http = new WebHelper();
        Livro novoLivro = null;
        final Gson parser = new Gson();

        @Override
        protected String doInBackground(String... params) {

            try {
                final String body = parser.toJson(livro, Livro.class);
                final WebResult webResult = http.executeHTTP(url, "POST", body);
                if(webResult.getHttpCode() == 200) {

                 novoLivro = parser.fromJson(webResult.getHttpBody(), Livro.class);
                }


            } catch (IOException e) {
                Log.d(TAG, "Exception calling add service", e);
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            edtDescricao.setText("");
            edtTitulo.setText("");
            edtGenero.setText("");
            edtAutor.setText("");

            Intent intent = new Intent();
            intent.putExtra("livro", livro);

            AddAnuncioActivity.this.setResult(1, intent);

            finish();

            Toast.makeText(AddAnuncioActivity.this, "Livro adicionado com sucesso!", Toast.LENGTH_SHORT).show();


        }


    }

    }




