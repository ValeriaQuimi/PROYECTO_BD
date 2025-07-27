package com.example.Entidades;

import com.example.enums.CategoriaCliente;

public class Cliente {

    private static int contador = 1;
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;
    private CategoriaCliente categoria;

    public Cliente( String nombre, String telefono, String direccion, String correo,
            CategoriaCliente categoria) {
        this.id = contador;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.categoria = categoria;
        contador++;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Cliente.contador = contador;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Teléfono: " + telefono + 
               ", Dirección: " + direccion + ", Correo: " + correo;
    }

}
