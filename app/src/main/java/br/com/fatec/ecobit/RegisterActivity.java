package br.com.fatec.ecobit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton btnVoltarLogin;
    private Button btnRegister;
    private EditText editName, editEmail, editPassword, editConfirmation;
    private ImageView btnImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        btnVoltarLogin = findViewById(R.id.btnImgBack);
        btnVoltarLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnRegister = findViewById(R.id.btnRegister);
        editName = findViewById(R.id.editRegName);
        editEmail = findViewById(R.id.editRegEmailAdress); // Verifique se o nome do ID está correto
        editPassword = findViewById(R.id.editRegPassword);
        editConfirmation = findViewById(R.id.editRegPasswordConfirmation);
        btnImgBack = findViewById(R.id.btnImgBack);

        btnRegister.setOnClickListener(view -> Register());

        btnImgBack.setOnClickListener(view -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Register() {
        String nameStr = editName.getText().toString();
        String emailStr = editEmail.getText().toString();
        String passwordStr = editPassword.getText().toString();
        String confirmationStr = editConfirmation.getText().toString();

        if (!nameStr.isEmpty() && !emailStr.isEmpty() && !passwordStr.isEmpty() && !confirmationStr.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                if (passwordStr.equals(confirmationStr)) {
                    AlertDialog alert = createAlertDialog();
                    alert.show();
                } else {
                    Toast.makeText(this, "Senhas Divergentes", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }
    }

    AlertDialog createAlertDialog() {
        String nameStr = editName.getText().toString();
        String emailStr = editEmail.getText().toString();
        String passwordStr = editPassword.getText().toString();
        Intent successReg = new Intent(this, MainActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cadastro realizado com sucesso\nSeja bem vind(o) " + nameStr);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            successReg.putExtra("email", emailStr);
            successReg.putExtra("password", passwordStr);
            startActivity(successReg);
        });
        return builder.create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Ciclo de vida", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Ciclo de vida", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ciclo de vida", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Ciclo de vida", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ciclo de vida", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Ciclo de vida", "onPause");
    }
}
