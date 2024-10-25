package cl.juanhernandez.myappbus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import cl.juanhernandez.myappbus.databinding.ActivityMainBinding;
import cl.juanhernandez.myappbus.ui.dashboard.DashboardFragment;
import cl.juanhernandez.myappbus.ui.home.HomeFragment;
import cl.juanhernandez.myappbus.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Configurar el callback de retroceso personalizado
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

                if (currentFragment instanceof HomeFragment) {

                } else if (currentFragment instanceof DashboardFragment) {
                    // Navega al HomeFragment
                    navController.navigate(R.id.navigation_home);
                } else if (currentFragment instanceof NotificationsFragment) {
                    // Navega al DashboardFragment
                    navController.navigate(R.id.navigation_dashboard);
                } else {
                    // Si no está en ningún fragmento específico, desactiva el callback
                    setEnabled(false);
                    onBackPressed();
                }
            }
        });
    }


}



/*
* // Muestra el cuadro de diálogo para confirmar cerrar sesión
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Cerrar sesión")
                            .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
* */