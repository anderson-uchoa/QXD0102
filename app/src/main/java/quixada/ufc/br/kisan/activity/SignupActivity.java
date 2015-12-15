package quixada.ufc.br.kisan.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import quixada.ufc.br.kisan.model.Usuario;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText nome_editText;
    private EditText email_editText;
    private EditText password_editText;
    private Button signupbutton;
    private TextView login_textView;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = new Usuario();

        nome_editText = (EditText) findViewById(R.id.input_name);
        email_editText = (EditText) findViewById(R.id.input_email);
        password_editText = (EditText) findViewById(R.id.input_password);
        signupbutton = (Button) findViewById(R.id.btn_signup);
        login_textView = (TextView) findViewById(R.id.link_login);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singup();
            }
        });

        login_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finalizar o registro na tela e voltar para loginActivity
                finish();
            }
        });


    }



    private void singup() {
        Log.d(TAG, "Signup");

        if (!validado()){
        onSigupFalhou();
            return;
        }

        signupbutton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando Account...");
        progressDialog.show();

        String name = nome_editText.getText().toString();
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        // TODO: logica signup aqui .

        usuario.setEmail(email);
        usuario.setNome(name);
        usuario.setPassword(password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSucesso();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    public void onSignupSucesso() {
        signupbutton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }



    private void onSigupFalhou() {

        Toast.makeText(getBaseContext(), "Falha no login", Toast.LENGTH_LONG).show();
        signupbutton.setEnabled(true);

    }

    private boolean validado() {
        boolean valido = true;

        String nome= nome_editText.getText().toString();
        String email = email_editText.getText().toString();
        String senha = password_editText.getText().toString();

        if (nome.isEmpty() || nome.length() < 3){
            nome_editText.setError("Deve conter mais de 3 caracteres");
        valido = false;
        }else {
            nome_editText.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             email_editText.setError("Entre com endereço de email valido");
             valido = false;
        }else {
             email_editText.setError(null);
        }

        if (senha.isEmpty() || senha.length() < 4 || senha.length() > 10) {
            password_editText.setError("4 a 10 caracteres");
            valido = false;
        } else {
            password_editText.setError(null);
        }

        return valido;
    }

}