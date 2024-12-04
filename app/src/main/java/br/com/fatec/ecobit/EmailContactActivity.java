package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class EmailContactActivity extends AppCompatActivity {

    private EditText edtSubject;
    private EditText edtMessage;
    private Button btnSendEmail;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_contact);

        btnBack = findViewById(R.id.btnVoltar);
        edtSubject = findViewById(R.id.Assunto);
        edtMessage = findViewById(R.id.Mensagem);
        btnSendEmail = findViewById(R.id.btnEnviar);

        // Botão de voltar
        btnBack.setOnClickListener(v-> finish());

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toEmail = "Ecobit.suporte@gmail.com"; // Email fixo
                String subject = edtSubject.getText().toString().trim();
                String message = edtMessage.getText().toString().trim();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Escolha o app de e-mail"));
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(EmailContactActivity.this, "Nenhum app de e-mail encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
