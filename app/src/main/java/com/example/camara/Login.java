package com.example.camara;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camara.Datos.UsuariosBD;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText txtUser;
    EditText txtPass;
    String nomreg;
    String cedreg;
    String userreg;
    String passreg;

    String email;
    String pass;

    TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       this.InicializarControles();
        this.Registro2();
    }

    public void Registro2(){

        //Este metodo me permite volver parte del TextView un Span(Texto con clickeable)

        registro=(TextView)findViewById(R.id.Registrar);

        //Guardo el mismo texto puesto en el Textview en una variable
        String text = "¿No tienes cuenta? Registrate";

        //Creo el objeto tipo SpannableString para volver parte del texto Span
        SpannableString res = new SpannableString(text);

        //Este objeto/metodo me permite convetir el texto Span en Clickeable
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick( View widget) {
                activity_Registro();
            }
            //El onClick que me manda a registro
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //y con este metodo le cambio el color al texto span para denotarlo
                super.updateDrawState(ds);
                ds.setColor(Color.GRAY);
                ds.setUnderlineText(false);
            }
        };

        //En esta funcion establezco que parte del texto quiero que sea el span
        res.setSpan(clickableSpan1, 19, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Mando el Texto nuevo al textView
        registro.setText(res);
        registro.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void InicializarControles(){
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
    }

    public void activity_Registro(){
        Intent reg = new Intent(this, SplashRegistro.class);
        startActivity(reg);
    }

    public void Ingresar(View view) {
        try {
            email = txtUser.getText().toString();
            pass = txtPass.getText().toString();

            UsuariosBD usuariosdb = new UsuariosBD(getApplicationContext(), "Login", null, R.integer.DBVersion);
            SQLiteDatabase db = usuariosdb.getReadableDatabase();

            String[] campos = new String[]{"nombre","correo", "contra"};

            Cursor c = db.query("usuarios", campos, null, null, null, null, null);

            if(email.equals("") || pass.equals("")){
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            } else if(!validarEmail(email)){
                txtUser.setError("Email no válido");
            } else if (c.moveToFirst()) {
                do{
                    nomreg = c.getString(0);
                    userreg = c.getString(1);
                    passreg = c.getString(2);

                    if (email.equals(userreg) && pass.equals(passreg)) {
                        Toast.makeText(getApplicationContext(), "¡Login!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, Main.class);
                        i.putExtra("nombre",nomreg);
                        i.putExtra("correo",userreg);
                        startActivity(i);
                        break;
                    }
                    Toast.makeText(getApplicationContext(), passreg, Toast.LENGTH_SHORT).show();

                }while (c.moveToNext());

                if(c.moveToNext() == false){
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }






    private boolean validarEmail(String txtUser) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(txtUser).matches();
    }
}