package br.com.fatec.ecobit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import br.com.fatec.ecobit.Retrofit.DoacaoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.Retrofit.UsuarioAPI;
import br.com.fatec.ecobit.model.Doacao;
import br.com.fatec.ecobit.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesDoacaoActivity extends AppCompatActivity {

    private ImageView imageDoacao, btnBack;
    private TextView textDescricaoDoacao, textCondicaoDoacao, textNumeroDoador, textLocalDoador, textEmailDoador, textNomeDoador;
    private Button btnChatDoador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_doacao);

        // Vincular elementos do layout
        textLocalDoador = findViewById(R.id.textLocalDoador);
        textNomeDoador = findViewById(R.id.textUpload);
        btnBack = findViewById(R.id.imageButton3);
        imageDoacao = findViewById(R.id.imageDoacao);
        textDescricaoDoacao = findViewById(R.id.textDescricaoDoacao);
        textCondicaoDoacao = findViewById(R.id.textCondicaoDoacao);
        textNumeroDoador = findViewById(R.id.textNumeroDoador);
        textLocalDoador = findViewById(R.id.textLocalDoador);
        textEmailDoador = findViewById(R.id.textEmailDoador);
        btnChatDoador = findViewById(R.id.btnChatDoador);

        // Botão de voltar
        btnBack.setOnClickListener( v -> finish());

        // Obter o ID da doação passada pela intent
        String doacaoId = getIntent().getStringExtra("DOACAO_ID");

        if (doacaoId != null) {
            fetchDoacaoDetails(doacaoId);
        } else {
            Toast.makeText(this, "Erro ao carregar detalhes da doação", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchDoacaoDetails(String id) {
        DoacaoAPI doacaoAPI = new RetrofitService().getRetrofit().create(DoacaoAPI.class);
        doacaoAPI.getDoacaoById(id).enqueue(new Callback<Doacao>() {
            @Override
            public void onResponse(Call<Doacao> call, Response<Doacao> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Doacao doacao = response.body();

                    // Preencher campos da doação
                    textDescricaoDoacao.setText(doacao.getDescricao());
                    textCondicaoDoacao.setText(doacao.getCondicao());
                    if (doacao.getImagensBase64() != null && !doacao.getImagensBase64().isEmpty()) {
                        byte[] decodedImage = Base64.decode(doacao.getImagensBase64().get(0), Base64.DEFAULT);
                        Glide.with(DetalhesDoacaoActivity.this).load(decodedImage).into(imageDoacao);
                    }

                    // Buscar dados do usuário associado
                    fetchUsuarioDetails(doacao.getUserId());
                } else {
                    Toast.makeText(DetalhesDoacaoActivity.this, "Detalhes não encontrados", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Doacao> call, Throwable t) {
                Toast.makeText(DetalhesDoacaoActivity.this, "Erro ao conectar ao servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void fetchUsuarioDetails(String userId) {
        UsuarioAPI usuarioAPI = new RetrofitService().getRetrofit().create(UsuarioAPI.class);
        usuarioAPI.getUserById(userId).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();

                    // Preencher campos do usuário
                    textNomeDoador.setText(usuario.getNome());
                    textNumeroDoador.setText("Telefone: " + usuario.getTelefone());
                    textEmailDoador.setText("Email: " + usuario.getemail());
                    textLocalDoador.setText("Endereço: \n" + usuario.getEndereco() + ", " +usuario.getCep());
                } else {
                    Toast.makeText(DetalhesDoacaoActivity.this, "Dados do usuário não encontrados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(DetalhesDoacaoActivity.this, "Erro ao buscar dados do usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }
}