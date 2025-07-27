package com.example.Entidades;

public class Repartidor {

    private static int contador= 1;
    private int id;
    private String nombre;
    private String telefono;

    public Repartidor(String nombre, String telefono) {
        this.id= contador;
        this.nombre = nombre;
        this.telefono = telefono;
        contador++;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Repartidor.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Repartidor [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }

    

}
