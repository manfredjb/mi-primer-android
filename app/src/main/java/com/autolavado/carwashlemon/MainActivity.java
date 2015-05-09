package com.autolavado.carwashlemon;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.Toast;


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

        correoi=(EditText)findViewById(R.id.txtcorreoi);
        passi=(EditText)findViewById(R.id.txtpass2);


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
        String correo1=correor.getText().toString();
        String tel1=tel.getText().toString();
        String pass1=passr.getText().toString();

        if (correo1.equals("") & tel1.equals("") & pass1.equals("")) {
            Toast.makeText(this, "Ha dejado campos vacios",
                    Toast.LENGTH_LONG).show();
        }
        else {

            AlertDialog.Builder dialogo4 = new AlertDialog.Builder(this);
            dialogo4.setTitle("Registro");
            dialogo4.setMessage("¿ Desea continuar ?");
            dialogo4.setCancelable(false);
            dialogo4.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    guardarusuario();
                }
            });
            dialogo4.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo4.show();

        }
    }

    public void guardarusuario(){
        String correo1=correor.getText().toString();
        String tel1=tel.getText().toString();
        String pass1=passr.getText().toString();
        int nro1=Integer.parseInt(tel1);
        int can =0;

        UsaBD admin = new UsaBD(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                "select correo from Usuarios where correo='" + correo1+"'", null);

        if (fila.moveToFirst()) {

            Toast.makeText(this, "YA existe el usuario",
                    Toast.LENGTH_SHORT).show();

        } else{
            ContentValues registro = new ContentValues();
            registro.put("correo",   correo1);
            registro.put("contra",      pass1);
            registro.put("telefono",      nro1);

            bd.insert("Usuarios", null, registro);

            Toast.makeText(this, "Usuario guardado",
                    Toast.LENGTH_SHORT).show();
            can=1;
        }


        bd.close();

        if(can==1 ) {
            User.setuser(correo1);
            llamarprincipal();

        }

    }

    public void iniciarsesion(View view){
        ingresar();
    }


    public void ingresar(){

        String correo2=correoi.getText().toString();
        String pass2=passi.getText().toString();

        UsaBD admin = new UsaBD(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                "select correo from Usuarios where correo='" + correo2+"' and contra='"+pass2+"'", null);

        if (fila.moveToFirst()) {
            bd.close();
            User.setuser(correo2);
            llamarprincipal();
        }
        else{
            Toast.makeText(this, "No existe el usuario",
            Toast.LENGTH_SHORT).show();
            bd.close();
        }



    }


    public void llamarprincipal(){
        Intent i= new Intent(this,principalActivity.class);
        startActivity(i);
    }

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

        bd.close();

    }
}