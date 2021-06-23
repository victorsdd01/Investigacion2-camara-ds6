package com.example.camara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Output;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camara.Datos.UsuariosBD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    public static final int PICK_IMAGE = 1002;
    Button capturar;
    ImageView imagensita;
    Uri uriImagen;
    TextView name;
    String name2;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        capturar=(Button)findViewById(R.id.btncap);
        imagensita=(ImageView)findViewById(R.id.Imagen);
        name = (TextView)findViewById(R.id.Name);
        CargarNombre();
    }

    public void CargarNombre(){
        Intent i = getIntent();
        name2 = i.getStringExtra("nombre");
        mail = i.getStringExtra("correo");
        name.setText(name2);
    }

    public void Camara(View view){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                String[] permisos={Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permisos,PERMISSION_CODE);
            }
            else{
                openCamera();
            }
        }
        else{
            openCamera();
        }
    }

    public void openGallery(View view) throws IOException {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void openCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Imagen Nueva");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Tomada desde la camara");
        uriImagen=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT,uriImagen);
        startActivityForResult(i,IMAGE_CAPTURE_CODE);
    }

    public void Enviar(View view){

        String uri2;
        uri2 = uriImagen.toString();

        Intent i= new Intent(this, Perfil.class);
        i.putExtra("imagen", uri2);
        i.putExtra("nombre",name2);
        i.putExtra("correo",mail);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this,"Permiso denegado",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            imagensita.setImageURI(uriImagen);
        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            uriImagen = data.getData();
            imagensita.setImageURI(uriImagen);
            Toast.makeText(this, "Foto desde la GALERIA", Toast.LENGTH_SHORT).show();
        }
    }
}
