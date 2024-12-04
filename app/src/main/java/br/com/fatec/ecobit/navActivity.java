package br.com.fatec.ecobit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.fatec.ecobit.home.view.chatFragment2;
import br.com.fatec.ecobit.home.view.ecoFragment;
import br.com.fatec.ecobit.home.view.prodsFragment;

public class navActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nav);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        // Verificar extra e abrir o fragmento correspondente
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("destination_fragment")) {
            int destinationFragment = intent.getIntExtra("destination_fragment", -1);
            if (destinationFragment == R.id.prodsFragment) {
                openFragment(new prodsFragment());
                bottomNavigationView.setSelectedItemId(R.id.prodsFragment);
            } else if (destinationFragment == R.id.ecoFragment) {
                openFragment(new ecoFragment());
                bottomNavigationView.setSelectedItemId(R.id.ecoFragment);
            } else if (destinationFragment == R.id.chatFragment) {
                openFragment(new chatFragment2());
                bottomNavigationView.setSelectedItemId(R.id.chatFragment);
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.prodsFragment) {
                    openFragment(new prodsFragment());
                } else if (item.getItemId() == R.id.ecoFragment) {
                    openFragment(new ecoFragment());
                } else if (item.getItemId() == R.id.chatFragment) {
                    openFragment(new chatFragment2());
                }
                return true;
            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.doacao) {
            startActivity(new Intent(this, AdicionarDoaActivity.class));
        } else if (id == R.id.perfil) {
            startActivity(new Intent(this, MyAccountActivity.class));
        } else if (id == R.id.menu_sair) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }
}
