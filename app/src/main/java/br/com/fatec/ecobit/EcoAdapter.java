package br.com.fatec.ecobit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.ecobit.R;
import br.com.fatec.ecobit.model.Ponto;

public class EcoAdapter extends RecyclerView.Adapter<EcoAdapter.EcoViewHolder> {

    private List<Ponto> ecopontos = new ArrayList<>();

    @NonNull
    @Override
    public EcoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ecoponto_item, parent, false);
        return new EcoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EcoViewHolder holder, int position) {
        Ponto ponto = ecopontos.get(position);

        holder.title.setText(ponto.getNomePonto());

        // Configura imagem padrão
        holder.imageView.setImageResource(R.drawable.icon);
    }

    @Override
    public int getItemCount() {
        return ecopontos.size();
    }

    public void setEcopontos(List<Ponto> ecopontos) {
        this.ecopontos = ecopontos;
        notifyDataSetChanged();
    }

    static class EcoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, address, materials, openOnSaturday;

        EcoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_ecoponto_img_grid);
            title = itemView.findViewById(R.id.item_ecoponto_title);
        }
    }
}
