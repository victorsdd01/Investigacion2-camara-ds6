package com.example.camara;

import android.content.ContentValues;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

import com.example.camara.Datos.UsuariosBD;

public class Registro extends AppCompatActivity {

    EditText contra,nomb,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //inicializo los controladores
        contra=(EditText)findViewById(R.id.contra_registro);
        nomb=(EditText)findViewById(R.id.nombre_registro);
        mail=(EditText)findViewById(R.id.correo_registro);
    }

    public void Enviar (View view) {

        if (Validar()) {

            //Guardo los datos introducidos en variables
            String contra1 = contra.getText().toString();
            String nombre1 = nomb.getText().toString();
            String correo1 = mail.getText().toString();

            UsuariosBD usuariosDb = new UsuariosBD(getApplicationContext(),"Login",null,R.integer.DBVersion);
            SQLiteDatabase db = usuariosDb.getWritableDatabase();

            if (db != null){
                ContentValues values = new ContentValues();
                values.put("nombre",nombre1);
                values.put("correo",correo1);
                values.put("contra", contra1);

                db.insert("usuarios",null,values);


                Intent i = new Intent(this, SplashFinalizado.class);
                startActivity(i);
            }
        }
    }

    public boolean Validar() {
        //Valida que se escriban datos
        boolean retorno = true;
        //Este objeto me permite validar si el formato es de un correo electronico
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        String Contra = contra.getText().toString();
        String Nombre = nomb.getText().toString();
        String Correo = mail.getText().toString();

        //Aqui corroboro si el correo introducido es correcto
        boolean email= pattern.matcher(Correo).matches();

        //Aqui comienzan los if de validacion
        if (Correo.isEmpty()) {
            mail.setError("Falta Correo");
            retorno = false;
        }
        if(!email && Correo != ""){
            mail.setError("Correo inválido");
            retorno = false;
        }
        if (Contra.isEmpty()) {
            contra.setError("Falta Contraseña");
            retorno = false;
        }
        if (Nombre.isEmpty()) {
            nomb.setError("Falta Nombre");
            retorno = false;
        }

        return retorno;
    }
}



