package br.com.fatec.ecobit.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fatec.ecobit.PontoDetalhesActivity;
import br.com.fatec.ecobit.R;
import br.com.fatec.ecobit.Retrofit.PontoAPI;
import br.com.fatec.ecobit.Retrofit.RetrofitService;
import br.com.fatec.ecobit.model.Ponto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ecoFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eco, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.ecoponto_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Grid com 2 colunas

        fetchEcopontos(new Callback<List<Ponto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ponto>> call, @NonNull Response<List<Ponto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(new EcoAdapter(response.body()));
                } else {
                    Toast.makeText(requireContext(), "Erro ao carregar ecopontos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ponto>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEcopontos(Callback<List<Ponto>> callback) {
        PontoAPI api = new RetrofitService().getRetrofit().create(PontoAPI.class);
        api.getPontos().enqueue(callback);
    }

    private static class EcoAdapter extends RecyclerView.Adapter<EcoAdapter.EcoViewHolder> {

        private final List<Ponto> ecopontos;

        public EcoAdapter(List<Ponto> ecopontos) {
            this.ecopontos = ecopontos;
        }

        @NonNull
        @Override
        public EcoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ecoponto_item, parent, false);
            return new EcoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EcoViewHolder holder, int position) {
            holder.bind(ecopontos.get(position));
        }

        @Override
        public int getItemCount() {
            return ecopontos.size();
        }

        static class EcoViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final TextView titleView;

            public EcoViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_ecoponto_img_grid);
                titleView = itemView.findViewById(R.id.item_ecoponto_title);
            }

            public void bind(Ponto ponto) {
                titleView.setText(ponto.getNomePonto());
                imageView.setImageResource(R.drawable.icon); // Configure imagem padrão

                // Adicione um clique ao item
                itemView.setOnClickListener(v -> {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PontoDetalhesActivity.class);
                    intent.putExtra("PONTO_ID", ponto.getId()); // Passe o ID do ecoponto
                    context.startActivity(intent);
                });
            }
        }
    }
}
