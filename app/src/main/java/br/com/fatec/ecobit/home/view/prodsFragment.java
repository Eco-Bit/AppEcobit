package br.com.fatec.ecobit.home.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.fatec.ecobit.DetalhesDoacaoActivity;
import br.com.fatec.ecobit.R;
import br.com.fatec.ecobit.Retrofit.DoacaoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.model.Doacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class prodsFragment extends Fragment {

    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_produtos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.produtos_rv);
        rv.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        // Carregar os dados inicialmente
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Atualizar os dados sempre que o fragmento for exibido novamente
        loadData();
    }

    private void loadData() {
        fetchDoacoes(new Callback<List<Doacao>>() {
            @Override
            public void onResponse(Call<List<Doacao>> call, Response<List<Doacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rv.setAdapter(new PostAdapter(response.body()));
                } else {
                    Toast.makeText(requireContext(), "Erro ao carregar doações", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doacao>> call, Throwable t) {
                Toast.makeText(requireContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDoacoes(final Callback<List<Doacao>> callback) {
        DoacaoAPI api = new RetrofitService().getRetrofit().create(DoacaoAPI.class);

        api.getDoacoes().enqueue(new Callback<List<Doacao>>() {
            @Override
            public void onResponse(Call<List<Doacao>> call, Response<List<Doacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    Toast.makeText(requireContext(), "Erro ao carregar doações", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doacao>> call, Throwable t) {
                Toast.makeText(requireContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
        private List<Doacao> doacoes;

        public PostAdapter(List<Doacao> doacoes) {
            this.doacoes = doacoes;
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.produto_item, parent, false);
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

        private static class PostViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView titleView;

            public PostViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_produto_img_grid);
                titleView = itemView.findViewById(R.id.item_produto_title);
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
                    Intent intent = new Intent(itemView.getContext(), DetalhesDoacaoActivity.class);
                    intent.putExtra("DOACAO_ID", doacao.getId()); // Passar o ID da doação
                    itemView.getContext().startActivity(intent);
                });
            }
        }
    }
}
