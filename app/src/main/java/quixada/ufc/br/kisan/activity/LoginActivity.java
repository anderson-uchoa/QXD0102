package quixada.ufc.br.kisan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import quixada.ufc.br.kisan.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText email_editText;
    private  EditText password_editText;
    private Button  login_button;
    private TextView signuptextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email_editText = (EditText) findViewById(R.id.input_email);
        password_editText = (EditText) findViewById(R.id.input_password);
        login_button = (Button) findViewById(R.id.btn_login);
        signuptextView = (TextView) findViewById(R.id.link_signup);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        signuptextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
/*
    private void login() {

        Log.d(TAG, "Efetuando login");

        if (!valido()){
            loginInvalidoMsg();
            return;
        }

      login_button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();


        String email = email_editText.getText().toString();
        String passaword = password_editText.getText().toString();


        if (email.equals("teste")  &&  passaword.equals("1234")){
            Intent intent = new Intent(getApplicationContext(), VisualizarAnuncioActivity.class);
            startActivity(intent);
        }


      new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        loginSucesso();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 30000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

              //logica do cadastro

                this.finish();
            }
        }
    }


    private void loginInvalidoMsg() {

        Toast.makeText(getBaseContext(), "Falha no Login", Toast.LENGTH_LONG).show();
        login_button.setEnabled(true);

    }

    private boolean valido() {
        boolean valido = true;
         String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_editText.setError("Entre com um endereço de email válido");
            valido = false;
        }else {
            email_editText.setError(null);

        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            password_editText.setError("Insira senha entre 4 a 10 caracteres");
            valido = false;
        }else {
            password_editText.setError(null);
        }

    return  valido;
    }

    //desativar voltar para MainActivity

   public  void loginSucesso (){
        login_button.setEnabled(true);
       finish();
    }
*/
}


