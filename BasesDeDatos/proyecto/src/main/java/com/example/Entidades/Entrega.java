package com.example.Entidades;

import java.util.Date;

import com.example.enums.EstadoEntrega;

public class Entrega {

    private static int contador=1;
    private int idEntrega;
    private Repartidor repartidor;
    private Date fechaEstimada;
    private EstadoEntrega estadoEntrega;
    private Cliente cliente;

    
    public Entrega(Repartidor repatidor, Date fechaEstimada, EstadoEntrega estadoEntrega, Cliente cliente) {
        this.idEntrega= contador;
        this.repartidor = repatidor;
        this.fechaEstimada = fechaEstimada;
        this.estadoEntrega = estadoEntrega;
        this.cliente = cliente;
        contador++;
    }


    public int getIdEntrega() {
        return idEntrega;
    }


    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }


    public Repartidor getRepatidor() {
        return repartidor;
    }


    public void setRepatidor(Repartidor repatidor) {
        this.repartidor = repatidor;
    }


    public Date getFechaEstimada() {
        return fechaEstimada;
    }


    public void setFechaEstimada(Date fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }


    public EstadoEntrega getEstadoEntrega() {
        return estadoEntrega;
    }


    public void setEstadoEntrega(EstadoEntrega estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }


    public Cliente getCliente() {
        return cliente;
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    @Override
    public String toString() {
        return "Entrega [idEntrega=" + idEntrega + ", repartidor=" + repartidor + ", fechaEstimada=" + fechaEstimada
                + ", estadoEntrega=" + estadoEntrega + ", cliente=" + cliente + "]";
    }

    
    
}
