package br.com.fatec.ecobit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.ecobit.Retrofit.DoacaoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.model.Doacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdicionarDoaActivity extends AppCompatActivity {

    private EditText editTitulo, editDescricao, editQuantidade, editCategoria, editCondicao, editDisponibilidade;
    private ImageView imgPreview, btnVoltar;
    private Button btnSalvar, btnSelecionarImagem;
    private List<String> imagensBase64 = new ArrayList<>(); // Lista de imagens em Base64

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_doa);

        // Configurar margens ajustáveis
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar campos e botões
        btnVoltar = findViewById(R.id.btnBack);
        editTitulo = findViewById(R.id.editTitulo);
        editDescricao = findViewById(R.id.editDescricao);
        editQuantidade = findViewById(R.id.editQuantidade);
        editCategoria = findViewById(R.id.editCategoria);
        editCondicao = findViewById(R.id.editCondicao);
        editDisponibilidade = findViewById(R.id.editDisponibilidade);
        imgPreview = findViewById(R.id.imgPreview);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem);

        // Configurar clique no botão de salvar
        btnSalvar.setOnClickListener(v -> salvarDoacao());

        // Configurar clique no botão para selecionar imagem
        btnSelecionarImagem.setOnClickListener(v -> selecionarImagem());

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void salvarDoacao() {
        // Obter valores dos campos
        String titulo = editTitulo.getText().toString();
        String descricao = editDescricao.getText().toString();
        String quantidadeStr = editQuantidade.getText().toString();
        String categoria = editCategoria.getText().toString();
        String condicao = editCondicao.getText().toString();
        String disponibilidade = editDisponibilidade.getText().toString();

        // Validar campos obrigatórios
        if (titulo.isEmpty() || descricao.isEmpty() || quantidadeStr.isEmpty() || categoria.isEmpty()
                || condicao.isEmpty() || disponibilidade.isEmpty()  || imagensBase64.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos e selecione ao menos uma imagem!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Converter quantidade para int
        int quantidade = Integer.parseInt(quantidadeStr);

        // Obter o ID do usuário do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        if (userId == null) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar objeto Doacao
        String telefone = "";
        Doacao doacao = new Doacao(titulo, descricao, quantidadeStr, categoria, condicao, disponibilidade, imagensBase64, userId, telefone);

        // Chamada à API
        DoacaoAPI doacaoAPI = new RetrofitService().getRetrofit().create(DoacaoAPI.class);
        Call<Doacao> call = doacaoAPI.salvarDoacao(doacao);

        call.enqueue(new Callback<Doacao>() {
            @Override
            public void onResponse(Call<Doacao> call, Response<Doacao> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdicionarDoaActivity.this, "Doação salva com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(AdicionarDoaActivity.this, "Erro ao salvar a doação!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Doacao> call, Throwable t) {
                Toast.makeText(AdicionarDoaActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        editTitulo.setText("");
        editDescricao.setText("");
        editQuantidade.setText("");
        editCategoria.setText("");
        editCondicao.setText("");
        editDisponibilidade.setText("");
        imgPreview.setImageResource(android.R.color.transparent);
        imagensBase64.clear(); // Limpar lista de imagens

        finish();
    }

    private void selecionarImagem() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            try {
                // Obter o fluxo de entrada da imagem selecionada
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;

                // Ler a imagem e escrever no ByteArrayOutputStream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = outputStream.toByteArray();
                // Converter para Base64 e adicionar à lista
                String imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                imagensBase64.add(imageBase64);

                // Exibir a imagem selecionada como pré-visualização
                Glide.with(this).load(data.getData()).into(imgPreview);

                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
