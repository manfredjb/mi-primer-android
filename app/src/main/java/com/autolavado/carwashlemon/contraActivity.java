package com.autolavado.carwashlemon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class contraActivity extends ActionBarActivity {
    private EditText vcontra, contran,contrann;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra);

        vcontra=(EditText)findViewById(R.id.txtcontact);
        contran=(EditText)findViewById(R.id.txtncon);
        contrann=(EditText)findViewById(R.id.txtrncon);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /** int id = item.getItemId();

         //noinspection SimplifiableIfStatement
         if (id == R.id.action_settings) {
         return true;
         }

         return super.onOptionsItemSelected(item);**/
        switch (item.getItemId()){
            case R.id.action_contra:
                llamarContra();
            case R.id.action_tel:
                llamarTel();
            case R.id.action_salir:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void llamarContra(){
        Intent i= new Intent(this,contraActivity.class);
        startActivity(i);

    }
    public void llamarTel(){
        Intent i= new Intent(this,telefonoActivity.class);
        startActivity(i);

    }
    public void BtnGuardarcontra(){
        String nc=contran.getText().toString();
        String nnc=contrann.getText().toString();
        String vc=vcontra.getText().toString();

        if (nc.equals("") & nnc.equals("") & vc.equals("")) {
            Toast.makeText(this, "Ha dejado campos vacios",
                    Toast.LENGTH_LONG).show();
        }
        else {
            if (nc == nnc) {


                AlertDialog.Builder dialogo4 = new AlertDialog.Builder(this);
                dialogo4.setTitle("Guardar");
                dialogo4.setMessage("¿ Desea continuar ?");
                dialogo4.setCancelable(false);
                dialogo4.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        guardarcontra();
                    }
                });
                dialogo4.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {}
                });
                dialogo4.show();
            }
            else {
                Toast.makeText(this, "Las contraseñas no coinciden",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void guardarcontra(){
        String u=User.getuser();
        String pass2=vcontra.getText().toString();
        String ncon=contran.getText().toString();

        UsaBD admin = new UsaBD(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select correo from Usuarios where correo='" + u+"' and contra='"+pass2+"'", null);

        if (fila.moveToFirst()) {

            ContentValues registro = new ContentValues();

            registro.put("contra", ncon);

            int cant = bd.update("Usuarios", registro, "contra=" + ncon, null);

            if (cant == 1){
                Toast.makeText(this, "Se actualizó el usuario", Toast.LENGTH_SHORT)
                        .show();}
            else{
                Toast.makeText(this, "No se pudo actualizar",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Contraseña incorrecta",
                    Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}