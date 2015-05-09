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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class telefonoActivity extends ActionBarActivity {
    private EditText tel2, contra2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        tel2=(EditText)findViewById(R.id.txttel2);
        contra2=(EditText)findViewById(R.id.txtcontratel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_telefono, menu);
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

    public void BtnGuardartel(View view){
        String tel=tel2.getText().toString();
        String contra=contra2.getText().toString();

        if (tel2.equals("") & contra.equals("")) {
            Toast.makeText(this, "Ha dejado campos vacios",
                    Toast.LENGTH_LONG).show();
        }
        else {

            AlertDialog.Builder dialogo4 = new AlertDialog.Builder(this);
            dialogo4.setTitle("Guardar");
            dialogo4.setMessage("¿ Desea continuar ?");
            dialogo4.setCancelable(false);
            dialogo4.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    guardartel();
                }
            });
            dialogo4.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo4.show();

        }
    }

    public void guardartel(){
        String u=User.getuser();
        String pass2=contra2.getText().toString();
        String tel=tel2.getText().toString();
        int num=Integer.parseInt(tel);
        Toast.makeText(this, tel, Toast.LENGTH_SHORT).show();

        UsaBD admin = new UsaBD(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select correo from Usuarios where correo='" + u+"' and contra='"+pass2+"'", null);

        if (fila.moveToFirst()) {

            ContentValues registro = new ContentValues();

            registro.put("telefono", num);

            int cant = bd.update("Usuarios", registro, "telefono=" + num, null);

            if (cant == 1){
                Toast.makeText(this, "Se actualizó el usuario", Toast.LENGTH_SHORT)
                        .show();}
            else{
                Toast.makeText(this, "No se pudo actualizar",
                        Toast.LENGTH_SHORT).show();

                /*Cursor fila2 = bd.rawQuery(
                        "select telefono from Usuario where correo = '" + u + "'", null);
                if (fila.moveToFirst()) {
                    Toast.makeText(this, "telefono: " + fila2.getInt(0), Toast.LENGTH_SHORT)
                            .show();
                }*/
            }
        }
        else{
            Toast.makeText(this, "Contraseña incorrecta",
                    Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}
