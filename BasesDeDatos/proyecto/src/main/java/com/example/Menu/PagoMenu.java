package com.example.Menu;

import java.util.Scanner;

import com.example.Service.PagoService;

public class PagoMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void gestionPagos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PAGOS ---");
            System.out.println("1. Registrar nuevo pago");
            System.out.println("2. Eliminar pago");
            System.out.println("3. Consultar pagos");
            System.out.println("4. Editar pago");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    PagoService.registrarPago();
                    break;
                case 2:
                    PagoService.eliminarPago();
                    break;
                case 3:
                    PagoService.consultarPagos();
                    break;
                case 4:
                    PagoService.editarPago();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
