package com.autolavado.carwashlemon;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class contraActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra);
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
    public void guardarcontra(){

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Verificación");
        dialog.setMessage("¿Desea guardar los cambios?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Aceptar",null);
        dialog.setNegativeButton("Cancelar",null);
        dialog.create();
        dialog.show();
    }

    public void llamarContra(){
        Intent i= new Intent(this,contraActivity.class);
        startActivity(i);

    }
    public void llamarTel(){
        Intent i= new Intent(this,telefonoActivity.class);
        startActivity(i);

    }
}
