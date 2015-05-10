package com.autolavado.carwashlemon;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Charles on 07/05/2015.
 */
public class UsaBD extends SQLiteOpenHelper{

    public static String DB = "administracion";
    public static int VERSION = 2;

    public UsaBD(Context context) {
        super(context, DB, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Usuarios" +
                "(correo text," +
                " contra text,"+
                "telefono numeric)");

        db.execSQL("create table Citas" +
                "(correo text," +
                "sucursal text,"+
                "hora int," +
                "fecha text)");

        db.execSQL("create table Horario(" +
                "hora int)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void guardarCita(Cita cita){
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("INSERT INTO Citas (correo, sucursal, hora, fecha) VALUES ('" + cita.getCorreo() + "', '" + cita.getSucursal() + "', " + cita.getHora() + ", '" + cita.fechaComoString() + "')");
    }

    public List<String> cargarHorasDisponibles(Sucursal sucursal, Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat(Cita.FORMATO_FECHA);
        String fechaFormateada = sdf.format(fecha);

        SQLiteDatabase bd = getWritableDatabase();
        //Cursor cursor = bd.rawQuery("select h.hora from Horario h, Citas c where h.hora != c.hora and c.fecha = '" + fechaFormateada + "' and c.sucursal = '" + sucursal.getNombre() + "'", null);
        Cursor cursor = bd.rawQuery("select h.hora from Horario h where h.hora not in (select c.hora from Citas c where c.fecha = '" + fechaFormateada + "' and c.sucursal = '" + sucursal.getNombre() + "')", null);
        List<String> horas = new ArrayList<String>();

        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int hora = cursor.getInt(0);
                horas.add(String.valueOf(hora));

            } while(cursor.moveToNext());
        }

        return horas;
    }
}

