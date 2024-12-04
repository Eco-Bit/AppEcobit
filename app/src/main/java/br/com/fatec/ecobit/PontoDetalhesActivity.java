package br.com.fatec.ecobit;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.fatec.ecobit.Retrofit.PontoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.model.Ponto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PontoDetalhesActivity extends AppCompatActivity {

    private TextView textNome, textEndereco, textNumero, textAbertoSabado;
    private ImageView imagePonto;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_detalhes);

        // Inicializar os componentes da interface
        btnBack = findViewById(R.id.imageButtonBack);
        textNome = findViewById(R.id.textNomeEcoponto);
        textEndereco = findViewById(R.id.textEnderecoEcoponto);
        textNumero = findViewById(R.id.textNumeroEcoponto);
        textAbertoSabado = findViewById(R.id.textAbertoSabado);
        imagePonto = findViewById(R.id.imageEcoponto);

        // Botão de voltar
        btnBack.setOnClickListener(v ->{
            finish();
        });

        // Obter o ID do ponto enviado pela Intent
        String pontoId = getIntent().getStringExtra("PONTO_ID");
        if (pontoId != null) {
            fetchPontoDetails(pontoId); // Buscar detalhes do ponto
        } else {
            Toast.makeText(this, "Erro ao carregar detalhes do ponto", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchPontoDetails(String id) {
        // Configuração da API
        PontoAPI api = new RetrofitService().getRetrofit().create(PontoAPI.class);

        // Chamada para obter os detalhes do ponto
        api.getPontoById(id).enqueue(new Callback<Ponto>() {
            @Override
            public void onResponse(Call<Ponto> call, Response<Ponto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Ponto ponto = response.body();

                    // Atualizar os campos com os dados do ponto
                    textNome.setText(ponto.getNomePonto());
                    textEndereco.setText("Endereço: " + ponto.getEndererecoPonto());
                    textNumero.setText("CEP: " + ponto.getNumeroPonto());
                    textAbertoSabado.setText("Aberto aos Sábados: " + ("true".equalsIgnoreCase(ponto.getAbertoSabado()) ? "Sim" : "Não"));

                } else {
                    Toast.makeText(PontoDetalhesActivity.this, "Ponto não encontrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Ponto> call, Throwable t) {
                Toast.makeText(PontoDetalhesActivity.this, "Erro ao buscar detalhes", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
