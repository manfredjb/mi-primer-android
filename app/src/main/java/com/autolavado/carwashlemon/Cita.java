package com.autolavado.carwashlemon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by manfred on 4/12/15.
 */
public class Cita {

    public static String FORMATO_FECHA = "dd/MM/yyyy";

    /**
     * el día y la hora de la cita
     */
    protected Date fecha;

    /**
     * el correo del cliente
     */
    protected String correo;

    /**
     * sucursal de atención de la cita
     */
    protected String sucursal;

    /**
     * hora de la cita
     */
    protected int hora;

    public Cita(String correo, String sucursal, int hora, Date fecha){
        this.correo = correo;
        this.sucursal = sucursal;
        this.hora = hora;
        this.fecha = fecha;
    }

    public String fechaComoString(){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.format(fecha);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
}
