package br.com.fatec.ecobit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.ecobit.Retrofit.DoacaoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.model.Doacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDoacoesActivity extends AppCompatActivity {

    private RecyclerView rv;
    private PostAdapter adapter;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doacoes);

        // Inicializar RecyclerView
        rv = findViewById(R.id.myDoacoes_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.btnBack);

        // Inicializar o adaptador com uma lista vazia
        adapter = new PostAdapter(new ArrayList<>());
        rv.setAdapter(adapter);

        // Carregar dados inicialmente
        loadData();

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarregar dados sempre que a Activity for exibida
        loadData();
    }

    private void loadData() {
        fetchDoacoes(new Callback<List<Doacao>>() {
            @Override
            public void onResponse(Call<List<Doacao>> call, Response<List<Doacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Atualizar o adaptador com os novos dados
                    adapter.updateData(response.body());
                } else {
                    Toast.makeText(MyDoacoesActivity.this, "Erro ao carregar doações", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doacao>> call, Throwable t) {
                Toast.makeText(MyDoacoesActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDoacoes(final Callback<List<Doacao>> callback) {
        DoacaoAPI api = new RetrofitService().getRetrofit().create(DoacaoAPI.class);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        api.getDoaUser(userId).enqueue(new Callback<List<Doacao>>() {
            @Override
            public void onResponse(Call<List<Doacao>> call, Response<List<Doacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    Toast.makeText(MyDoacoesActivity.this, "Erro ao carregar doações", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doacao>> call, Throwable t) {
                Toast.makeText(MyDoacoesActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
        private final List<Doacao> doacoes;

        public PostAdapter(List<Doacao> doacoes) {
            this.doacoes = doacoes;
        }

        public void updateData(List<Doacao> newDoacoes) {
            doacoes.clear();
            doacoes.addAll(newDoacoes);
            notifyDataSetChanged();
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_doacao1, parent, false);
            return new PostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            holder.bind(doacoes.get(position));
        }

        @Override
        public int getItemCount() {
            return doacoes.size();
        }

        class PostViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView, btnDelete;
            private final TextView titleView;

            public PostViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageUserDoacao);
                titleView = itemView.findViewById(R.id.itemUserTilte);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }

            public void bind(Doacao doacao) {
                titleView.setText(doacao.getTitulo());

                // Configurar imagem
                if (doacao.getImagensBase64() != null && !doacao.getImagensBase64().isEmpty()) {
                    byte[] decodedString = android.util.Base64.decode(doacao.getImagensBase64().get(0), android.util.Base64.DEFAULT);
                    Glide.with(itemView.getContext()).load(decodedString).into(imageView);
                } else {
                    imageView.setImageResource(R.drawable.icon); // Imagem padrão
                }

                // Adicionar clique para abrir os detalhes
                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(itemView.getContext(), EditaDoacaoActivity.class);
                    intent.putExtra("DOACAO_ID", doacao.getId()); // Passar o ID da doação
                    itemView.getContext().startActivity(intent);
                });

                btnDelete.setOnClickListener(v -> {
                    // Instanciar o serviço Retrofit
                    DoacaoAPI api = new RetrofitService().getRetrofit().create(DoacaoAPI.class);

                    // Fazer a chamada DELETE na API usando o ID da doação
                    api.deleteDoa(doacao.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // Mensagem de sucesso
                                Toast.makeText(itemView.getContext(), "Doação deletada com sucesso", Toast.LENGTH_SHORT).show();

                                // Reiniciar a atividade MyDoacoesActivity
                                Context context = itemView.getContext();
                                Intent intent = new Intent(context, MyDoacoesActivity.class);
                                context.startActivity(intent);

                                // Finalizar a atividade atual
                                if (context instanceof MyDoacoesActivity) {
                                    ((MyDoacoesActivity) context).finish();
                                }
                            } else {
                                // Exibir mensagem de erro para códigos de status inesperados
                                Toast.makeText(itemView.getContext(), "Erro ao deletar: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Mensagem de erro de conexão
                            Toast.makeText(itemView.getContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });


            }
        }
    }
}
