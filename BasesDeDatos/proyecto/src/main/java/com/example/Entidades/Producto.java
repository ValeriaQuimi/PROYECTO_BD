package com.example.Entidades;

import com.example.enums.EstadoProducto;
import com.example.enums.TipoProducto;

public class Producto {

    private static int contador=1;
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;
    private int stockMinimo;
    private TipoProducto tipoProducto;
    private int categoria;
    private EstadoProducto estado;

    

    public Producto() {
    }

    public Producto(String nombre, String descripcion, double precio,
            int cantidadDisponible, int stockMinimo, int categoria, EstadoProducto estado, TipoProducto tipoProducto) {
        this.idProducto = contador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.stockMinimo = stockMinimo;
        this.categoria = categoria;
        this.estado = estado;
        this.tipoProducto= tipoProducto;
        contador++;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Producto.contador = contador;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String toString() {
        return "ID: " + idProducto + ", Nombre: " + nombre + ", Precio: " + precio+
        " Categoría: " + categoria + ", Estado: " + estado + ", Tipo: " + categoria+
        " Cantidad disponible: " + cantidadDisponible + ", Stock mínimo: " + stockMinimo+
        " Descripción: " + descripcion;
    }
}
