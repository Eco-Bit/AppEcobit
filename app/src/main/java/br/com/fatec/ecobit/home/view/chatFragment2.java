package br.com.fatec.ecobit.home.view;

import static java.nio.file.Files.find;

import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import br.com.fatec.ecobit.R;
import br.com.fatec.ecobit.myListAdapter;


public class chatFragment2 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.chat_rv);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new PostAdapter());
        rv.setHasFixedSize(true);
    }

    private static class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            holder.bind(R.drawable.ic_perfil);
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        static class PostViewHolder extends RecyclerView.ViewHolder {
            PostViewHolder(View itemView) {
                super(itemView);
            }

            void bind(int image) {
                ((ImageView) itemView.findViewById(R.id.imageView)).setImageResource(image);
            }
        }
    }

}