package cl.juanhernandez.myappbus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    //Atributos
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.idCorreo);
        editTextPassword = findViewById(R.id.idClave);


        //Boton loguear
        Button ingreso = (Button) findViewById(R.id.botonIngresar);
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        //Botton crear perfil
        Button creoP = (Button) findViewById(R.id.botonCrearUsuario);
        creoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigationC = new Intent(Login.this, Register.class);
                startActivity(navigationC);
            }
        });
    }

    private void loginUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //verificar los campos si están vacios
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Por favor, ingrese su correo");
            return;
        }

        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Por favor, ingrese su clave");
            return;
        }

        //Autenticar al usuario
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user != null){
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(Login.this, "Error al iniciar sesión: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}