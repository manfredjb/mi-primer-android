package com.autolavado.carwashlemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manfred on 4/11/15.
 */
public class Sucursal {

    public static List<Sucursal> sucursales = Arrays.asList(
        new Sucursal("San Nicolás", R.drawable.san_nico, "san nicolas"),
        new Sucursal("Guadalupe", R.drawable.gpe, "guadalupe"),
        new Sucursal("Monterrey", R.drawable.mty, "monterrey")
    );

    public static String SUCURSAL_POR_DEFECTO = "San Nicolás";

    protected String nombre;
    protected int imagen;
    protected String direccion;

    public Sucursal(String nombre, int imagen, String direccion){
        this.nombre = nombre;
        this.imagen = imagen;
        this.direccion = direccion;
    }

    public static Sucursal buscarPorNombre(String nombre){
        Sucursal sucursal = null;

        for(Sucursal s : sucursales){
            if (s.getNombre().equalsIgnoreCase(nombre)){
                sucursal = s;
                break;
            }
        }

        return sucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
