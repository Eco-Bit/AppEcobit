package br.com.fatec.ecobit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.Retrofit.UsuarioAPI;
import br.com.fatec.ecobit.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnVoltar;
    private Button btnCancel, btnProSave;

    private EditText editProName, editProTel, editProEmail, editProPassword, editProConfirm, editProCep, editProEndereco;

    private UsuarioAPI usuarioAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        initializeComponents();

        usuarioAPI = new RetrofitService().getRetrofit().create(UsuarioAPI.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVoltar.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());
        btnProSave.setOnClickListener(v -> updateUserProfile());

        loadUserData();
    }

    public void initializeComponents() {
        btnVoltar = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnProCancel);
        btnProSave = findViewById(R.id.btnProSave);
        editProName = findViewById(R.id.editProName);
        editProTel = findViewById(R.id.editProTel);
        editProEmail = findViewById(R.id.editProEmail);
        editProPassword = findViewById(R.id.editProPassword);
        editProConfirm = findViewById(R.id.editProConfirm);
        editProCep = findViewById(R.id.editProCep);
        editProEndereco = findViewById(R.id.editProEndereco);
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        if (userId == null) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        usuarioAPI.getUserById(userId).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    // Preencher os campos com os dados do usuário
                    editProName.setText(usuario.getNome());
                    editProTel.setText(usuario.getTelefone());
                    editProEmail.setText(usuario.getemail());
                    editProCep.setText(usuario.getCep());
                    editProEndereco.setText(usuario.getEndereco());
                } else {
                    Toast.makeText(ProfileActivity.this, "Erro ao carregar dados do usuário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserProfile() {
        // Recupera o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);
        String userEmail = sharedPreferences.getString("user_email", null); // Recupera o email salvo no login

        if (userId == null || userEmail == null) {
            Toast.makeText(this, "Erro: Informações do usuário não encontradas!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação dos campos obrigatórios
        if (editProName.getText().toString().isEmpty() ||
                editProTel.getText().toString().isEmpty() ||
                editProEmail.getText().toString().isEmpty() ||
                editProCep.getText().toString().isEmpty() ||
                editProEndereco.getText().toString().isEmpty()) {

            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se os campos estiverem preenchidos, cria o objeto Usuario e envia a atualização
        Usuario usuario = new Usuario();
        usuario.setId(userId); // Adiciona o ID do usuário ao objeto
        usuario.setNome(editProName.getText().toString());
        usuario.setTelefone(editProTel.getText().toString());
        usuario.setemail(userEmail); // Certifique-se de enviar o email salvo no backend
        usuario.setCep(editProCep.getText().toString());
        usuario.setEndereco(editProEndereco.getText().toString());

        String password = editProPassword.getText().toString();
        String confirmPassword = editProConfirm.getText().toString();
        if (!password.isEmpty() && password.equals(confirmPassword)) {
            usuario.setSenha(password);
        } else if (!password.isEmpty()) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Envia o objeto usuario com o ID para o back-end
        usuarioAPI.updateUser(userId, usuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.d("ProfileActivity", "Perfil atualizado com sucesso");
                } else {
                    Toast.makeText(ProfileActivity.this, "Erro ao atualizar perfil!", Toast.LENGTH_SHORT).show();
                    Log.e("ProfileActivity", "Erro ao atualizar perfil: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProfileActivity", "Erro de conexão: " + t.getMessage(), t);
            }
        });
    }
}
