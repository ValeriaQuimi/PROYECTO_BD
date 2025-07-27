package com.example.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.example.Entidades.Pago;
import com.example.Entidades.Pedido;
import com.example.enums.MetodoDePago;

public class PagoService {

    private static List<Pago> pagos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void registrarPago() {
        try {
            System.out.println("\n=== REGISTRAR PAGO ===");

            System.out.print("Monto total: ");
            int monto = Integer.parseInt(sc.nextLine());

            System.out.print("Fecha de pago (dd/MM/yyyy): ");
            String fechaStr = sc.nextLine();
            Date fechaPago = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);

            System.out.println("Métodos de pago disponibles:");
            for (MetodoDePago metodo : MetodoDePago.values()) {
                System.out.println("- " + metodo);
            }
            System.out.print("Seleccione método de pago: ");
            String metodoStr = sc.nextLine().toUpperCase();

            MetodoDePago metodoPago;
            try {
                metodoPago = MetodoDePago.valueOf(metodoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Método de pago inválido. Pago cancelado.");
                return;
            }

            System.out.print("Ingrese número de orden del pedido a pagar: ");
            int idPedido = Integer.parseInt(sc.nextLine());

            Pedido pedido = PedidoService.buscarPedidoPorId(idPedido); // Este método debe existir
            if (pedido == null) {
                System.out.println("Pedido no encontrado. Pago cancelado.");
                return;
            }

            Pago pago = new Pago(monto, fechaPago, metodoPago, pedido);
            pagos.add(pago);
            System.out.println("Pago registrado con ID: " + pago.getIdPago());

        } catch (Exception e) {
            System.out.println("Error en la entrada de datos. Pago no registrado.");
        }
    }

    public static Pago buscarPago(int idPago) {
        for (Pago p : pagos) {
            if (p.getIdPago() == idPago) {
                return p;
            }
        }
        return null;
    }

}
