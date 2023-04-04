package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText codigo;
    EditText nombre;
    EditText precio;

    Button btnInsertar;
    Button btnEliminar;
    Button btnConsultar;
    Button btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = findViewById(R.id.txtCodigo);
        nombre = findViewById(R.id.txtNombre);
        precio = findViewById(R.id.txtPrecio);

        btnConsultar = findViewById(R.id.btnConsultar);
        btnInsertar = findViewById(R.id.btnInsertar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnModificar = findViewById(R.id.btnModificar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar(view);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(view);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(view);
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });

    }

    public void insertar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String sCodigo = codigo.getText().toString();
        String sNombre = nombre.getText().toString();
        String sPrecio = precio.getText().toString();
        if(!sCodigo.isEmpty() && !sNombre.isEmpty() && !sPrecio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",sCodigo);
            registro.put("nombre", sNombre);
            registro.put("precio",sPrecio);
            baseDatos.insert("articulos",null,registro);
            baseDatos.close();
            codigo.setText("");
            nombre.setText("");
            precio.setText("");
            Toast.makeText(this,"Almacenado correctamente",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Debe completar todos los espacios.",Toast.LENGTH_LONG).show();
        }

    }

    public void consultar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String sCodigo = codigo.getText().toString();
        if(!sCodigo.isEmpty()){
            Cursor fila = baseDatos.rawQuery("select nombre, precio from articulos where codigo = " + sCodigo,null);
            if(fila.moveToFirst()){
                nombre.setText(fila.getString(0));
                precio.setText(fila.getString(1));
            }else{
                Toast.makeText(this,"Datos NO encontrados!",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Debe introducir el codigo para buscar",Toast.LENGTH_LONG).show();
        }
        baseDatos.close();
    }

    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String sCodigo = codigo.getText().toString();
        if(!sCodigo.isEmpty()){
            int cantidad = baseDatos.delete("articulos", "codigo = "+ sCodigo,null);
            if(cantidad == 1){
                Toast.makeText(this,"Datos borrados con exito!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Articulo no encontrado.",Toast.LENGTH_LONG).show();
            }
            baseDatos.close();
            codigo.setText("");
            nombre.setText("");
            precio.setText("");
        }else{
            Toast.makeText(this,"Debe introducir el codigo para eliminarlo.",Toast.LENGTH_LONG).show();
        }
    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String sCodigo = codigo.getText().toString();
        String sNombre = nombre.getText().toString();
        String sPrecio = precio.getText().toString();
        if(!sCodigo.isEmpty() && !sNombre.isEmpty() && !sPrecio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", sCodigo);
            registro.put("nombre", sNombre);
            registro.put("precio", sPrecio);
            int cantidad = baseDatos.update("articulos",registro,"codigo = "+ sCodigo,null);
            baseDatos.close();
            if(cantidad == 1){
                Toast.makeText(this,"Articulo actualizado con exito :)",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Articulo NO actualizado :(",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Debe completar todos los campos.",Toast.LENGTH_SHORT).show();
        }
    }

}