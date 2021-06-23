package com.example.camara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camara.Datos.UsuariosBD;

public class Perfil extends AppCompatActivity {
    ImageView imagensita;
    String uri2;
    Uri uri;
    TextView name;
    TextView mail;
    String name2;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        imagensita=(ImageView)findViewById(R.id.imagen);
        name = findViewById(R.id.Name);
        mail= findViewById(R.id.Correo);
        cargarImagen();
        cargarDatos();

    }

    public void cargarImagen(){
        Intent i = getIntent();
        uri2 = i.getStringExtra("imagen");


        uri= Uri.parse(uri2);

        imagensita.setImageURI(uri);
    }
    public void cargarDatos(){

        Intent i = getIntent();
        name2 = i.getStringExtra("nombre");
        email = i.getStringExtra("correo");
        name.setText(name2);
        mail.setText(email);
    }

    public void Salir(View view){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }


}