package com.example.Service;

import java.util.Scanner;

public class PedidoService {

    private static Scanner sc = new Scanner(System.in);

    public static void crearPedido() {
        System.out.println("\n=== CREAR PEDIDO ===");

        // 👉 Buscar cliente
        System.out.print("Ingrese ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

    }

    public static void listarPedidos() {
        System.out.println("\n=== LISTADO DE PEDIDOS ===");

    }

    public static void eliminarPedidos() {

    }

}
