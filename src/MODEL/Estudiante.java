/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import javafx.scene.paint.Color;

/**
 *
 * @author Leonardo
 */
public class Estudiante{
    private String nombre;
    private int aportaciones;
    private int palabras;
    private int cantidadImagenes;
    private Color color;

    public Estudiante(String nombre) {
        this.nombre = nombre;
        this.aportaciones = 0;
        this.palabras = 0;
        this.cantidadImagenes = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAportaciones() {
        return aportaciones;
    }

    public void setAportaciones(int aportaciones) {
        this.aportaciones = aportaciones;
    }

    public int getPalabras() {
        return palabras;
    }

    public void setPalabras(int palabras) {
        this.palabras = palabras;
    }

    public int getCantidadImagenes() {
        return cantidadImagenes;
    }

    public void setCantidadImagenes(int cantidadImagenes) {
        this.cantidadImagenes = cantidadImagenes;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
