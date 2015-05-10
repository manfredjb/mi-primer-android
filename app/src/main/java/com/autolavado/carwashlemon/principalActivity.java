package com.autolavado.carwashlemon;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import android.content.Intent;


public class principalActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


//        getActionBar().setDisplayHomeAsUpEnabled(true);
        /*
         * Vamos a llenar el spinner de horas
         */
        llenarhorarios(); // crear los registros de horas en la base de datos
        mostrarHoras(); // mostrar solo las horas disponibles

        /*
         * Creamos una vector con los nombres de las sucursales para agregarlas al spinner
         * correspondiente
         */
        String[] nombres = new String[Sucursal.sucursales.size()];

        for (int i = 0; i < Sucursal.sucursales.size(); i ++){
            nombres[i] = Sucursal.sucursales.get(i).getNombre();
        }

        /*
         * acá vamos a crear el combox box de las sucursales del tab "sucursales", la idea de este
         * combobox es simplemente mostrar la información de la sucursal seleccionada
         */
        final Spinner sucursalesInformativas = (Spinner) findViewById(R.id.nombres_sucursales);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombres);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sucursalesInformativas.setAdapter(spinnerArrayAdapter);


        /*
         * este evento se dispara cada vez que el usuario seleccione una sucursal del tab de
         * información de sucursales.
         *
         */
        sucursalesInformativas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // el nombre de la sucursal seleccionada
                String nombreSucursal = sucursalesInformativas.getSelectedItem().toString();

                // llamamos al mÃ©todo encargado de mostrar los detalles de la sucursal
                mostrarSucursal(nombreSucursal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        /*
         * acá vamos a crear el combox box de las sucursales del tab "sucursales", la idea de este
         * combobox es simplemente mostrar la información de la sucursal seleccionada
         */
        final Spinner sucursalesSeleccionables = (Spinner) findViewById(R.id.sucursales_para_citas);
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombres);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sucursalesSeleccionables.setAdapter(spinnerArrayAdapter);


        /*
         * este evento se dispara cada vez que el usuario seleccione una sucursal del tab de
         * citas.
         *
         * - cada vez que una sucursal es seleccionada, se debe de consultar la base de datos para
         * actualizar el combo de horas disponibles
         *
         */
        sucursalesSeleccionables.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mostrarHoras();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*
         * Vamos a agregar un evento para el calendario cada vez que este cambia de fecha
         */
        final CalendarView calendario = (CalendarView)findViewById(R.id.calendario);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mostrarHoras();
            }
        });



        TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec("Nueva cita");
        spec1.setContent(R.id.tabCita);
        spec1.setIndicator("Nueva cita");

        TabHost.TabSpec spec2=tabHost.newTabSpec("Sucursales");
        spec2.setIndicator("Sucursales");
        spec2.setContent(R.id.tabSucursales);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

    }

    /**
     * Mostra los detalles de la sucursal seleccionada
     *
     * - actualiza el widget de nombre
     * - actualiza el wiget de direccion
     * - actualiza el widget de imagen
     *
     * @param nombre
     */
    public void mostrarSucursal(String nombre){
        for (int i = 0; i < Sucursal.sucursales.size(); i ++){
            Sucursal sucursal = Sucursal.sucursales.get(i);

            if (sucursal.getNombre().equalsIgnoreCase(nombre)){
                // asignamos el nombre
                TextView viewNombre = (TextView) findViewById(R.id.sucursal_nombre);
                viewNombre.setText(sucursal.getNombre());

                // asignamos el la direccion
                TextView viewDireccion = (TextView) findViewById(R.id.sucursal_direccion);
                viewDireccion.setText(sucursal.getDireccion());

                // asignamos la image
                ImageView viewImagen = (ImageView) findViewById(R.id.sucursal_imagen);
                viewImagen.setImageResource(sucursal.getImagen());
            }

        }
    }

    /**
     * Este evento es llamado cada vez que el botÃ³n "Guardar" es clickeado. AcÃ¡ guardamos la cita,
     * pero inicialmente solo mostraremos un mensaje de confirmaciÃ³n :D
     * @param botonGuardar
     */
    public void guardarCita(View botonGuardar){
        /*
         * Extraer la fecha seleccionada
         */
        CalendarView calendario = (CalendarView)findViewById(R.id.calendario);
        Date fechaSeleccionada = new Date();


        /*
         * Extraer la hora
         */
        Spinner spinnerHoras = (Spinner)findViewById(R.id.horas);
        int hora = Integer.parseInt(spinnerHoras.getSelectedItem().toString());

        /*
         * correo del usuario
         */
        String correo = "ejemplo@ejemplo.com";

        /*
         * sucursal
         */
        final Spinner sucursalesSeleccionables = (Spinner) findViewById(R.id.sucursales_para_citas);
        String sucursal = sucursalesSeleccionables.getSelectedItem().toString();

        /**
         * Finalmente guardamos la cita
         */
        UsaBD admin = new UsaBD(this);
        Cita cita = new Cita(correo, sucursal, hora, fechaSeleccionada);
        admin.guardarCita(cita);

        // mostrar una confirmacion
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, "La cita se ha guardado con éxito", duration);
        toast.show();

        mostrarHoras();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
           // return true;
       //}
        switch (item.getItemId()){
            case R.id.action_contra:{
                llamarContra();
                break;
            }

            case R.id.action_tel:{
                llamarTel();
                break;
            }

            case R.id.action_salir:{
                finish();
                break;
            }


            default:{
                return super.onOptionsItemSelected(item);
            }

        }

        return true;
    }


    public void llamarContra(){
        Intent i= new Intent(this,contraActivity.class);
        startActivity(i);

    }
    public void llamarTel(){
        Intent i= new Intent(this,telefonoActivity.class);
        startActivity(i);

    }

    public void mostrarHoras(){

        /**
         * Extraemos la sucursal seleccionada
         */
        final Spinner sucursalesSeleccionables = (Spinner) findViewById(R.id.sucursales_para_citas);
        String nombreSucursal = sucursalesSeleccionables.getSelectedItem() == null
            ? Sucursal.SUCURSAL_POR_DEFECTO
            : sucursalesSeleccionables.getSelectedItem().toString();
        Sucursal sucursal = Sucursal.buscarPorNombre(nombreSucursal);

        /**
         * Extraemos la fecha seleccionada
         */
        CalendarView calendario = (CalendarView)findViewById(R.id.calendario);
        Date fechaSeleccionada = new Date();

        if (0 < calendario.getDate()){
            fechaSeleccionada.setTime(calendario.getDate());
        }

        /**
         * Listo! vamos a cargar las horas disponibles para esta sucursal y esta fecha
         */
        UsaBD admin = new UsaBD(this);
        List<String> horas = admin.cargarHorasDisponibles(sucursal, fechaSeleccionada);


        final Spinner spinnerHoras = (Spinner) findViewById(R.id.horas);
        ArrayAdapter<String> spinnerHorasArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, horas);
        spinnerHorasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHoras.setAdapter(spinnerHorasArrayAdapter);
    }

    public void llenarhorarios(){

        UsaBD admin = new UsaBD(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                "select hora from Horario", null);
        if (!fila.moveToFirst()) {
            for (int i = 10; i < 21; i++) {
                ContentValues re = new ContentValues();
                re.put("hora", i);
                bd.insert("Horario", null, re);
            }
        }

        bd.close();

    }
}
