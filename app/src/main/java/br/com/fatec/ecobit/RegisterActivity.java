package br.com.fatec.ecobit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.Retrofit.UsuarioAPI;
import br.com.fatec.ecobit.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText editName, editEmail, editPassword, editConfirmation;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        initializeComponents();

        btnRegister.setOnClickListener(view -> Register());
        btnBack.setOnClickListener(view -> finish());
    }

    private void initializeComponents() {
        btnRegister = findViewById(R.id.btnRegister);
        editName = findViewById(R.id.editRegName);
        editEmail = findViewById(R.id.editRegEmailAdress);
        editPassword = findViewById(R.id.editRegPassword);
        editConfirmation = findViewById(R.id.editRegPasswordConfirmation);
        btnBack = findViewById(R.id.btnBack);
    }

    public void Register() {
        String nameStr = editName.getText().toString();
        String emailStr = editEmail.getText().toString();
        String passwordStr = editPassword.getText().toString();
        String confirmationStr = editConfirmation.getText().toString();

        if (nameStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty() || confirmationStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordStr.equals(confirmationStr)) {
            Toast.makeText(this, "Senhas Divergentes", Toast.LENGTH_SHORT).show();
            return;
        }
        
        registerUser(nameStr, emailStr, passwordStr);
    }

    private void registerUser(String name, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setNome(name);
        usuario.setemail(email);
        usuario.setSenha(password);

        UsuarioAPI apiUsuario = new RetrofitService().getRetrofit().create(UsuarioAPI.class);
        apiUsuario.save(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    createAlertDialog(name).show();
                    clearFields();
                } else {
                    Toast.makeText(RegisterActivity.this, "Falha ao cadastrar. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, "Falha na conexão. Tente novamente.", Toast.LENGTH_SHORT).show();
                Log.e("RegisterActivity", "Error: " + throwable.getMessage());
            }
        });
    }

    private void clearFields() {
        editName.setText("");
        editEmail.setText("");
        editPassword.setText("");
        editConfirmation.setText("");
    }

    AlertDialog createAlertDialog(String nameStr) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cadastro realizado com sucesso\nSeja bem vindo(a) " + nameStr);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        return builder.create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Ciclo de vida", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Ciclo de vida", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ciclo de vida", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Ciclo de vida", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ciclo de vida", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Ciclo de vida", "onPause");
    }
}
