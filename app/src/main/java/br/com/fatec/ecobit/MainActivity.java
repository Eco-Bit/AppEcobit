package br.com.fatec.ecobit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.Retrofit.UsuarioAPI;

import java.util.Map;

import br.com.fatec.ecobit.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnCadastro;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCreateAcount);
        editEmail = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);

        btnLogin.setOnClickListener(view -> login());

        btnCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    public void login() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setemail(email);
        usuario.setSenha(password);

        UsuarioAPI api = new RetrofitService().getRetrofit().create(UsuarioAPI.class);
        api.login(usuario).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> responseBody = response.body();
                    String id = (String) responseBody.get("id"); // Obtém o ID do usuário
                    if (id != null) {
                        // Armazenar o ID e o email no SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", id);
                        editor.putString("user_email", email); // Armazena o email para futuras requisições
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                        // Redirecionar para a HomeActivity
                        Intent intent = new Intent(MainActivity.this, navActivity.class);
                        intent.putExtra("destination_fragment", R.id.prodsFragment);
                        startActivity(intent);
                        finish(); // Impedir que o usuário volte ao login ao pressionar "Voltar"
                    } else {
                        Toast.makeText(MainActivity.this, "Falha ao fazer login. Verifique as credenciais.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Falha ao fazer login. Verifique as credenciais.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
