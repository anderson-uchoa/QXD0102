package quixada.ufc.br.kisan.activity;

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


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Autor;
import quixada.ufc.br.kisan.model.Genero;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class AddAnuncioActivity extends AppCompatActivity {


//    String url = "http://10.0.2.2:8080/KisanSERVER/livros";
String url = "http://10.0.2.2:8080/KisanSERVER/livros";
    private static final String TAG = "AddAnuncioActivity";


    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtGenero;
    private EditText edtAutor;
    private Button addLivro;

    private String arquivo;

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
        edtGenero =  (EditText) findViewById(R.id.input_genero_livro);

        livro = new Livro();


        CustomApplication customApplication = (CustomApplication) getApplicationContext();
        livro.setUsuario(customApplication.getUsuario());



        addLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                livro.setTitulo(edtTitulo.getText().toString());
                livro.setSinopse(edtDescricao.getText().toString());


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

            Toast.makeText(AddAnuncioActivity.this, "Livro adicionado com sucesso!", Toast.LENGTH_SHORT).show();


        }
    }


/*
    private class adiocionarLivro extends AsyncTask<String, Void, String> {

        final WebHelper http = new WebHelper();
        Livro novoLivro = null;
        final Gson parser = new Gson();

        @Override
        protected String doInBackground(String... params) {

            enableViews(false);
            try {

                final String body = parser.toJson(livro, Livro.class);
                final WebResult webResult = http.executeHTTP(url, "POST", body);
                if (webResult.getHttpCode() == 200) {

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
            enableViews(true);

            edtDescricao.setText("");
            edtTitulo.setText("");
            edtGenero.setText("");
            edtAutor.setText("");

            Toast.makeText(AddAnuncioActivity.this, "Livro adicionado com sucesso!", Toast.LENGTH_SHORT).show();


        }
    }

        public void callntentImgCam(View view) {

            File file = new File(Environment.getExternalStorageDirectory(), "img.pmg");
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, IMG_CAM);


        }

        public void callntentImgSDCard(View view) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("img/");
            startActivityForResult(intent, IMG_SDCARD);

        }


        private void enableViews(boolean status) {
            edtTitulo.setEnabled(status);
            edtAutor.setEnabled(status);
            edtGenero.setEnabled(status);
            edtDescricao.setEnabled(status);
            addLivro.setEnabled(status);


        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      File file = null;

       if (data != null && requestCode == IMG_SDCARD && resultCode == RESULT_OK){

           Uri img = data.getData();
           String [] cols = {MediaStore.Images.Media.DATA};
           Cursor cursor = getContentResolver().query(img, cols,null, null, null);
           cursor.moveToFirst();

           int indexCol = cursor.getColumnIndex(cols[0]);
           String imgString = cursor.getString(indexCol);
           cursor.close();

           file = new File(imgString);
           if(file != null){
               livro.getFoto().setResizedBitmap(file, 50, 50);
               livro.getFoto().setMimeFromImgPath(file.getPath());

           }

       }else if (requestCode == IMG_CAM && resultCode == RESULT_OK){

           file = new File(Environment.getExternalStorageDirectory(),"img.png");
           if(file != null){
               livro.getFoto().setResizedBitmap(file, 50, 50);
               livro.getFoto().setMimeFromImgPath(file.getPath());

            }

       }

        if (livro.getFoto().getBitmap() != null){
            imageView.setImageBitmap(livro.getFoto().getBitmap());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */
}


