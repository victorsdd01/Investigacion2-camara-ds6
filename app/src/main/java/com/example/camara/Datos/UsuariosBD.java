package com.example.camara.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosBD extends SQLiteOpenHelper {

    String tableUsuarios = "CREATE TABLE usuarios(nombre TEXT, correo TEXT, contra TEXT)";

    public UsuariosBD(Context context, String nombre, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,nombre,cursorFactory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableUsuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

}
