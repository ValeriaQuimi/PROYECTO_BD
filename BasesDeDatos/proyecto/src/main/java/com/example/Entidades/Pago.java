package com.example.Entidades;

import java.util.Date;

import com.example.enums.MetodoDePago;

public class Pago {

    private static int contador= 1;
    private int idPago;
    private int montoTotal;
    private Date fechaPago;
    private MetodoDePago metodoDePago;
    private Pedido pedido;
    
    public Pago( int montoTotal, Date fechaPago, MetodoDePago metodoDePago, Pedido pedido) {
        this.idPago = contador;
        this.montoTotal = montoTotal;
        this.fechaPago = fechaPago;
        this.metodoDePago = metodoDePago;
        this.pedido= pedido;
        contador++;
    }
    public int getIdPago() {
        return idPago;
    }
    public int getMontoTotal() {
        return montoTotal;
    }
    public Date getFechaPago() {
        return fechaPago;
    }
    public MetodoDePago getMetodoDePago() {
        return metodoDePago;
    }


    public static int getContador() {
        return contador;
    }
    public static void setContador(int contador) {
        Pago.contador = contador;
    }
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    public void setMetodoDePago(MetodoDePago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    @Override
    public String toString() {
        return "Pago [idPago=" + idPago + ", montoTotal=" + montoTotal + ", fechaPago=" + fechaPago + ", metodoDePago="
                + metodoDePago + "]";
    }


    
    

}
