package com.example.Entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.enums.EstadoPedido;
import com.example.enums.TipoEntrega;

public class Pedido {
    private static int contador = 0;
    private int numOrden;
    private int montoTotal;
    private Date fechaEntrega;
    private TipoEntrega tipoEntrega;
    private EstadoPedido estado;

    private Cliente cliente; 
    private List<Producto> productos; 

    public Pedido(Cliente cliente, List<Producto> productos, int montoTotal, Date fechaEntrega, TipoEntrega tipoEntrega,
            EstadoPedido estado) {
        this.numOrden = ++contador;
        this.cliente = cliente;
        this.productos = productos;
        this.montoTotal = montoTotal;
        this.fechaEntrega = fechaEntrega;
        this.tipoEntrega = tipoEntrega;
        this.estado = estado;
    }

    
    public static int getContador() {
        return contador;
    }


    public static void setContador(int contador) {
        Pedido.contador = contador;
    }


    public int getNumOrden() {
        return numOrden;
    }


    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }


    public int getMontoTotal() {
        return montoTotal;
    }


    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }


    public Date getFechaEntrega() {
        return fechaEntrega;
    }


    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }


    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }


    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }


    public EstadoPedido getEstado() {
        return estado;
    }


    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }


    // Getters y setters
    public Cliente getCliente() {
        return cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public String toString() {
        return "Pedido [Número: " + numOrden + ", Cliente: " + cliente.getNombre() + ", Productos: " + productos.size()
                +
                ", Monto Total: " + montoTotal + ", Fecha de Entrega: " +
                new SimpleDateFormat("dd/MM/yyyy").format(fechaEntrega) + ", Tipo de Entrega: " +
                tipoEntrega + ", Estado: " + estado + " ]";
    }
}