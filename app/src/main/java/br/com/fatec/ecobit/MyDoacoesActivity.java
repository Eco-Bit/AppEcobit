package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyDoacoesActivity extends AppCompatActivity {

    private ImageView btnVoltarPerfil2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_doacoes);

        initializeComponents();

        btnVoltarPerfil2.setOnClickListener(view -> finish());

        // Iniciando a lista
        ListView listView = findViewById(R.id.listaMyDoacao);

        // Data - Array [Title, subtitle, Imagens]
        String[] mainTitle = {
                "Mesa usada",
                "Cabiceira bem conservada",
                "Sapato semi novo",
                "Roupas velhas e usadas",
                "Televisor de 32 polegadas"
        };

        String[] subTitle = {
                "ow, você pode vir",
                "Ok, combinado",
                "Novinho, não quero mais",
                "Roupas infantis essas",
                "Televisor é de tubo, mas tá bonzinho"
        };

        int[] imagensArray = {
                R.drawable.mesa_usada,
                R.drawable.cabiceira,
                R.drawable.sapato,
                R.drawable.ropas_infantil,
                R.drawable.televisor
        };

        // Adapter - Certifique-se de que essa classe esteja definida
        myListAdapter adapter = new myListAdapter(this, mainTitle, subTitle, imagensArray);
        listView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeComponents(){
        btnVoltarPerfil2 = findViewById(R.id.imageButton5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Ciclo de vida MyDoacoes", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Ciclo de vida MyDoacoes", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ciclo de vida MyDoacoes", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Ciclo de vida MyDoacoes", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ciclo de vida MyDoacoes", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Ciclo de vida MyDoacoes", "onPause");
    }
}