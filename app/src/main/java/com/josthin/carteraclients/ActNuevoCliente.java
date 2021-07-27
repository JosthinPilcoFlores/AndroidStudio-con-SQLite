package com.josthin.carteraclients;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.josthin.carteraclients.BaseDatos.DatosOpenHelper;

public class ActNuevoCliente extends AppCompatActivity {
    private EditText edtNombre;
    private EditText edtDireccion;
    private EditText edtEmail;
    private EditText edtTelefono;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_nuevo_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelefono = (EditText) findViewById(R.id.edtTelefono);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_nuevo_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.action_ok:
                if (bCamposCorrectos()){
                    try{
                        datosOpenHelper = new DatosOpenHelper(this);
                        conexion = datosOpenHelper.getWritableDatabase();
                        StringBuilder sql = new StringBuilder();
                        sql.append("INSERT INTO CLIENTE (NOMBRE,DIRECCION,EMAIL,TELEFONO) VALUES ('");
                        sql.append(edtNombre.getText().toString().trim()+"', '");
                        sql.append(edtDireccion.getText().toString().trim()+"', '");
                        sql.append(edtEmail.getText().toString().trim()+"', '");
                        sql.append(edtTelefono.getText().toString().trim()+"')");

                        conexion.execSQL(sql.toString());
                        conexion.close();

                        finish();
                    }catch (Exception ex){
                        AlertDialog.Builder dig = new AlertDialog.Builder(this);
                        dig.setTitle("Aviso");
                        dig.setMessage(ex.getMessage());
                        dig.setNeutralButton("OK",null);
                        dig.show();
                    }
                }else {
                    AlertDialog.Builder dig = new AlertDialog.Builder(this);
                    dig.setTitle("Aviso");
                    dig.setMessage("Exiten campos vacíos");
                    dig.setNeutralButton("OK",null);
                    dig.show();
                }
                break;
            case R.id.action_cancelar:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean bCamposCorrectos(){
        boolean res = true;
        if (edtNombre.getText().toString().trim().isEmpty()){
            edtNombre.requestFocus();
            res = false;
        }
        if (edtDireccion.getText().toString().trim().isEmpty()){
            edtNombre.requestFocus();
            res = false;
        }
        if (edtEmail.getText().toString().trim().isEmpty()){
            edtNombre.requestFocus();
            res = false;
        }
        if (edtTelefono.getText().toString().trim().isEmpty()){
            edtNombre.requestFocus();
            res = false;
        }
        return res;
    }
}