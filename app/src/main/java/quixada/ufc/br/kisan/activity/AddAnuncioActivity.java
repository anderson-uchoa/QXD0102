package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.Util.CaminhosWebService;
import quixada.ufc.br.kisan.Util.RealPathUtil;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class AddAnuncioActivity extends AppCompatActivity {

    private static final String TAG = "AddAnuncioActivity";

    String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/livros";
    String urlfoto = "http://"+ CaminhosWebService.IP+"/KisanSERVER/file";

    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtGenero;
    private EditText edtAutor;
    private Button addLivro;
    private ImageView addImagemLivro;

    private Livro livro;

    String caminho;
    boolean capturouImagem = true;

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
        addImagemLivro = (ImageView) findViewById(R.id.imagem_livro_add);

        livro = new Livro();


        CustomApplication customApplication = (CustomApplication) getApplication();
        livro.setUsuario(customApplication.getUsuario());


    addImagemLivro.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
           Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
           intent.setType("image/*");
           startActivityForResult(intent,1);

    }
});

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

   public void run(String caminho) {

       final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                   .setType(MultipartBody.FORM)
               .addFormDataPart("file", "imagem.png", RequestBody.create(MEDIA_TYPE_PNG, new File(caminho))).build();

       Request request = new Request.Builder()
               .url(urlfoto)
               .post(requestBody)
               .build();

       Response response = null;
       try {
           response = client.newCall(request).execute();

           if (response.isSuccessful()){
               livro.setFoto(response.body().string());
           }

       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        capturouImagem = true;
        final Uri uri = data.getData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                caminho = RealPathUtil.getPath(getApplicationContext(),uri);
                Log.i(TAG, caminho);
                AddAnuncioActivity.this.run(caminho);
            }
        }).start();

    }


    private class adiocionarLivro extends AsyncTask<String, Void, String> {

        final WebHelper http = new WebHelper();
        Long id  = null;
        final Gson parser = new Gson();

        @Override
        protected String doInBackground(String... params) {

            try {
                final String body = parser.toJson(livro, Livro.class);
                final WebResult webResult = http.executeHTTP(url, "POST", body);
                if(webResult.getHttpCode() == 200) {
                    try {
                        id = Long.parseLong(webResult.getHttpBody());
                    }catch (NumberFormatException e) {
                        Log.d(TAG, "Erro conversao long", e);
                    }

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
            livro.setId(id);
            intent.putExtra("livro", livro);

            AddAnuncioActivity.this.setResult(1, intent);

            finish();

            Toast.makeText(AddAnuncioActivity.this, "Livro adicionado com sucesso!", Toast.LENGTH_SHORT).show();

        }

    }

}




