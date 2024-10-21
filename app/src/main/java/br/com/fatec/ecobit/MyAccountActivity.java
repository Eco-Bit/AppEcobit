package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyAccountActivity extends AppCompatActivity {
    private ImageView btnVoltarHome2;
    private Button btnDoacao, btnSair, btnPerfil;
    private Button btnFaq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_account);

        initializeComponents();

        btnVoltarHome2.setOnClickListener(view ->
                finish());

        btnPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        btnDoacao.setOnClickListener(v -> {
            startActivity(new Intent(this,MyDoacoesActivity.class));
        });

        btnFaq.setOnClickListener((view ->{
            startActivity(new Intent(this, FACActivity.class));

        }));

        btnSair.setOnClickListener((view ->{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initializeComponents(){
        btnVoltarHome2 = findViewById(R.id.imageButton3);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnDoacao = findViewById(R.id.btnDoacao);
        btnFaq = findViewById(R.id.btnFaq);
        btnSair = findViewById(R.id.btnSair);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Ciclo de vida MyAccount", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Ciclo de vida MyAccount", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ciclo de vida MyAccount", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Ciclo de vida MyAccount", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ciclo de vida MyAccount", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Ciclo de vida MyAccount", "onPause");
    }
}