package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyAccountActivity extends AppCompatActivity {
    private ImageButton btnVoltarHome2;
    private Button btnDoacao;
    private Button btnFaq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_account);

        btnVoltarHome2 = findViewById(R.id.imageButton3);
        btnDoacao = findViewById(R.id.btnDoacao);
        btnFaq = findViewById(R.id.btnFaq);

        btnVoltarHome2.setOnClickListener(view ->{
            startActivity(new Intent(this, HomeActivity.class));
        });
        btnDoacao.setOnClickListener(v -> {
            startActivity(new Intent(this,MyDoacoesActivity.class));
        });

        btnFaq.setOnClickListener((view ->{
            startActivity(new Intent(this, FACActivity.class));
        }));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}