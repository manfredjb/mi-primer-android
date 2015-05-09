package com.autolavado.carwashlemon;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.EditText;;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {
    private EditText correor,tel,passr;
    private EditText correoi,passi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        correor=(EditText)findViewById(R.id.txtcorreo);
        tel=(EditText)findViewById(R.id.txttel);
        passr=(EditText)findViewById(R.id.txtpass);


        TabHost tabHost=(TabHost)findViewById(R.id.tabHostHome);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec("Registro");
        spec1.setContent(R.id.tabRegistro);
        spec1.setIndicator("Registro");

        TabHost.TabSpec spec2=tabHost.newTabSpec("Iniciar Sesion");
        spec2.setIndicator("Iniciar_Sesion");
        spec2.setContent(R.id.tabiniciar);


        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        llenarhorarios();

    }
    public void Registrarse(View view) {

        /** String correo1=correor.getText().toString();
         String tel1=tel.getText().toString();
         String pass1=passr.getText().toString();
         int nro1=Integer.parseInt(tel1);

         AlertDialog.Builder dialog=new AlertDialog.Builder(this);
         dialog.setTitle("Datos");
         dialog.setMessage(correo1+tel1+pass1);
         dialog.setCancelable(false);
         dialog.setPositiveButton("OK",null);
         dialog.create();
         dialog.show();**/

        llamarprincipal();



    }

    public void iniciarsesion(View view){

        /**String correo2=correoi.getText().toString();
         String pass2=passi.getText().toString();


         AlertDialog.Builder dialog=new AlertDialog.Builder(this);
         dialog.setTitle("Confirmación");
         dialog.setMessage(correo2+pass2);
         dialog.setCancelable(false);
         dialog.setPositiveButton("OK",null);
         dialog.create();
         dialog.show();**/
        //llamarprincipal();


    }

    public void llamarprincipal(){
        Intent i= new Intent(this,principalActivity.class);
        startActivity(i);
    }


    /**  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
    }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
     // Handle action bar item clicks here. The action bar will
     // automatically handle clicks on the Home/Up button, so long
     // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();

     //noinspection SimplifiableIfStatement
     if (id == R.id.action_settings) {
     return true;
     }

     return super.onOptionsItemSelected(item);
     }**/

    public void llenarhorarios(){

        UsaBD admin = new UsaBD(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                "select hora from Horario", null);
        if (fila.moveToFirst()) {}

        else {

            for (int i = 10; i < 21; i++) {
                ContentValues re = new ContentValues();
                re.put("hora", i);
                bd.insert("Horario", null, re);
            }
        }


    }
}