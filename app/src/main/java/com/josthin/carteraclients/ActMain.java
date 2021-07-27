package com.josthin.carteraclients;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.josthin.carteraclients.BaseDatos.DatosOpenHelper;

import java.util.ArrayList;

public class ActMain extends AppCompatActivity {
    private ListView lstDatos;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> clientes;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActMain.this,ActNuevoCliente.class);
                startActivityForResult(it,0);
            }
        });

        actualizar();
    }

    private void actualizar(){
        lstDatos = (ListView) findViewById(R.id.lstDatos);
        clientes = new ArrayList<String>();

        try{
            datosOpenHelper = new DatosOpenHelper(this);
            conexion = datosOpenHelper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE");
            String aNombre;
            String aTelefono;

            Cursor resultado = conexion.rawQuery(sql.toString(),null);

            if (resultado.getCount()>0){
                resultado.moveToFirst();
                do {
                    aNombre = resultado.getString(resultado.getColumnIndex("NOMBRE"));
                    aTelefono = resultado.getString(resultado.getColumnIndex("TELEFONO"));
                    clientes.add(aNombre + ": " + aTelefono);
                }
                while (resultado.moveToNext());
            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,clientes);
            lstDatos.setAdapter(adaptador);
        }
        catch (Exception ex){
            AlertDialog.Builder dig = new AlertDialog.Builder(this);
            dig.setTitle("Aviso");
            dig.setMessage(ex.getMessage());
            dig.setNeutralButton("OK",null);
            dig.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        actualizar();
    }
}