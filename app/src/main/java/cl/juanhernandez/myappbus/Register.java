package cl.juanhernandez.myappbus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    //Variables
    private EditText nombreEditText, correoEditText, claveEditText;
    private Button botonCrearUsuario;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nombreEditText = findViewById(R.id.idNombreRegistro);
        correoEditText = findViewById(R.id.idCorreoRegistro);
        claveEditText = findViewById(R.id.idClaveRegistro);
        botonCrearUsuario = findViewById(R.id.botonCrearUsuarioO);

        botonCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = nombreEditText.getText().toString().trim();
                String correo = correoEditText.getText().toString().trim();
                String clave = claveEditText.getText().toString().trim();

                if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(clave)){
                    Toast.makeText(Register.this, "Por favor, complete todos los campos!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser(nombre, correo, clave);
            }
        });

    }

    private void registerUser(String nombre, String correo, String clave){
        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                saveUserToFirestore(user.getUid(), nombre, correo);
                            }
                        }else{
                            Toast.makeText(Register.this, "Error1 al registrar usuario: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToFirestore(String userId, String nombre, String correo){
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre);
        user.put("email", correo);
        user.put("role", "usuario");

        db.collection("Users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Register.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    // Redirigir al MainActivity
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e ->{
                    Toast.makeText(Register.this, "Error al guardar el usuario en Firestore", Toast.LENGTH_SHORT).show();
                });

    }


}