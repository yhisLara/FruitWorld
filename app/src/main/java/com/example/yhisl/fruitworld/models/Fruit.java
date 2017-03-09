package com.example.yhisl.fruitworld.models;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yhisl on 08/03/2017.
 */

public class Fruit {

    private String nombre;
    private String origen;
    private int icono;

    public Fruit(){}

    public Fruit(String nombre, String origen, int icono){
        this.nombre = nombre;
        this.origen = origen;
        this.icono = icono;
    }

    public String getName(){return nombre;}

    public String getOrigen(){return origen;}

    public void setName(String nombre){this.nombre = nombre;}

    public void setIcon(int icono){this.icono = icono;}

    public int getIcon(){return icono;}







}
