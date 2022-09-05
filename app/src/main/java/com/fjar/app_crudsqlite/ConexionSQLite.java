package com.fjar.app_crudsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ConexionSQLite extends SQLiteOpenHelper {

    public ConexionSQLite(Context context) {
        super(context, "administracion.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("Create table articulos(codigo integer not null primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists articulos");
        onCreate(db);
    }

    public SQLiteDatabase db(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }

    //Método que inserta los datos en la tabla de forma tradicional
    public boolean InsertarTradicional (Dto datos){
        boolean estado = true;
        int resultado;

        try{
            int codigo = datos.getCodigo();
            String desc = datos.getDescripcion();
            double precio = datos.getPrecio();

            //Cursor fila = this.getWritableDatabase().rawQuery("Select codigo from articulos where codigo == " + codigo, null);
            Cursor fila = db().rawQuery("Select codigo from articulos Where codigo == " + codigo, null);
            if (fila.moveToFirst()){
                estado = false;

            }else {
                //estado = (Boolean) this.getWritableDatabase().insert("datos", "nombre, correo, telefono", registro);
                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres, apellidos, usuario, clave, pregunta, respuesta", registro);
                String SQL = "Insert Into articulos \n" +
                            "(codigo, descripcion, precio) \n" +
                            "Values \n "+
                            "('" + String.valueOf(codigo) + "', '" + desc + "', '" + String.valueOf(precio) + "');";

                db().execSQL(SQL);
                db().close();
                /*
                this.getWritableDatabase().execSQL(SQL);
                this.getWritableDatabase().close();
                 */
                estado = true;
            }
        }catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }
}
