package com.example.Menu;

import java.util.Scanner;

import com.example.Service.PedidoService;

public class PedidoMenu {

    
    private static Scanner sc = new Scanner(System.in);


    public static void gestionPedidos() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ DE PEDIDOS ===");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Eliminar pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    PedidoService.crearPedido();
                    break;
                case 2:
                    PedidoService.listarPedidos();
                    break;
                case 3:
                    PedidoService.eliminarPedidos();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo del menú...");
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        }
    }

}
