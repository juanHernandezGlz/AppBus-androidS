package cl.juanhernandez.myappbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Boton loguear
        Button ingreso = (Button) findViewById(R.id.botonIngresar);
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nav = new Intent(Login.this, MainActivity.class);
                startActivity(nav);
            }
        });

        //Botton crear perfil
        Button creoP = (Button) findViewById(R.id.botonCrearUsuario);
        creoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigationC = new Intent(Login.this, MainActivity.class);
                startActivity(navigationC);
            }
        });
    }
}