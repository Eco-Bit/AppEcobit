package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnCadastro;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initializeComponents();

        btnLogin.setOnClickListener(view -> {
            Login();
        });

        btnCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Login() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeComponents(){
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCreateAcount);
        editEmail = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Ciclo de vida Main", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ciclo de vida Main", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String email = bundle.getString("email", " ");
            String password = bundle.getString("password", " ");
            editEmail.setText(email);
            editPassword.setText(password);
        }
        Log.i("Ciclo de vida Main","OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ciclo de vida Main", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Ciclo de vida Main", "onPause");
    }
}
