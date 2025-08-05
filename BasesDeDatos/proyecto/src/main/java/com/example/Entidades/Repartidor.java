package com.example.Entidades;

public class Repartidor {

    private int id;
    private String nombre;
    private int telefono;

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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Repartidor [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }

    

}
