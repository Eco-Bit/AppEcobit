package br.com.fatec.ecobit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.fatec.ecobit.Retrofit.DoacaoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.model.Doacao;
import br.com.fatec.ecobit.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditaDoacaoActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText editTitulo, editQtd, editCategoria, editCond, editDisp, editDescricao;
    private Button btnSalvar;
    private DoacaoAPI doacaoAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edita_doacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        editTitulo = findViewById(R.id.editTituloUpdate);
        editQtd = findViewById(R.id.editQuantidadeUpdate);
        editCategoria = findViewById(R.id.editCategoriaUpdate);
        editCond = findViewById(R.id.editCondicaoUpdate);
        editDisp = findViewById(R.id.editDisponibilidadeUpdate);
        editDescricao = findViewById(R.id.editDescricaoUpdate);
        btnSalvar = findViewById(R.id.btnSalvarUpdate);
        doacaoAPI = new RetrofitService().getRetrofit().create(DoacaoAPI.class);

        loadUserData();

        btnSalvar.setOnClickListener(v -> {
            updateDoacao();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadUserData() {
        Bundle bundle = getIntent().getExtras();
        String doacaoId = bundle.getString("DOACAO_ID");

        if (doacaoId == null) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        doacaoAPI.getDoacaoById(doacaoId).enqueue(new Callback<Doacao>() {
            @Override
            public void onResponse(Call<Doacao> call, Response<Doacao> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Doacao doacao = response.body();
                    // Preencher os campos com os dados do usuário
                    editTitulo.setText(doacao.getTitulo());
                    editQtd.setText(doacao.getQuantidade());
                    editCategoria.setText(doacao.getCategoria());
                    editCond.setText(doacao.getCondicao());
                    editDisp.setText(doacao.getDisponibilidade());
                    editDescricao.setText(doacao.getDescricao());
                } else {
                    Toast.makeText(EditaDoacaoActivity.this, "Erro ao carregar dados do usuário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Doacao> call, Throwable t) {
                Toast.makeText(EditaDoacaoActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDoacao() {
        // Recuperar o ID da doação da Intent
        String doacaoId = getIntent().getStringExtra("DOACAO_ID");
        if (doacaoId == null) {
            Toast.makeText(this, "Erro: ID da doação não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buscar os dados atuais da doação
        doacaoAPI.getDoacaoById(doacaoId).enqueue(new Callback<Doacao>() {
            @Override
            public void onResponse(Call<Doacao> call, Response<Doacao> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Preencher os campos não alterados com os dados existentes
                    Doacao doacaoAtual = response.body();

                    // Atualizar somente os campos modificados
                    doacaoAtual.setTitulo(editTitulo.getText().toString());
                    doacaoAtual.setQuantidade(editQtd.getText().toString());
                    doacaoAtual.setCategoria(editCategoria.getText().toString());
                    doacaoAtual.setCondicao(editCond.getText().toString());
                    doacaoAtual.setDisponibilidade(editDisp.getText().toString());
                    doacaoAtual.setDescricao(editDescricao.getText().toString());

                    // Enviar a atualização para o backend
                    enviarAtualizacao(doacaoId, doacaoAtual);
                } else {
                    Toast.makeText(EditaDoacaoActivity.this, "Erro ao buscar dados da doação!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Doacao> call, Throwable t) {
                Toast.makeText(EditaDoacaoActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarAtualizacao(String doacaoId, Doacao doacaoAtual) {
        doacaoAPI.updateDoacao(doacaoId, doacaoAtual).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditaDoacaoActivity.this, "Doação atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Finaliza a Activity e volta para a anterior
                } else {
                    Toast.makeText(EditaDoacaoActivity.this, "Erro ao atualizar a doação!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditaDoacaoActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}