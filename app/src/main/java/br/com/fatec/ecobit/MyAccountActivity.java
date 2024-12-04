package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyAccountActivity extends AppCompatActivity {
    private Button btnSair, btnPerfil,btnDoacao, btnContato;
    private Button btnFaq;
    private ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_account);

        initializeComponents();

        btnBack.setOnClickListener(v -> finish());

        btnDoacao.setOnClickListener(v -> {
            startActivity(new Intent(this, MyDoacoesActivity.class));
        });

        btnPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        btnFaq.setOnClickListener((view ->{
            startActivity(new Intent(this, FACActivity.class));

        }));

        btnSair.setOnClickListener((view ->{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }));
        btnContato.setOnClickListener((view ->{
            startActivity(new Intent(this, EmailContactActivity.class));
            finish();
        }));





    }


    private void initializeComponents(){
        btnDoacao = findViewById(R.id.btnDoacao);
        btnBack = findViewById(R.id.btnVoltar);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnFaq = findViewById(R.id.btnFaq);
        btnSair = findViewById(R.id.btnSair);
        btnContato= findViewById(R.id.btnContato);
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