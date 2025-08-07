package com.example.Menu;

import java.util.Scanner;
import com.example.Service.DetalleService;

public class DetalleMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void gestionDetalles() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTION DE DETALLES ---");
            System.out.println("1. Registrar nuevo detalle");
            System.out.println("2. Eliminar detalle");
            System.out.println("3. Consultar detalles");
            System.out.println("4. Editar detalle");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            String input = sc.nextLine();
            int opcion;

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    DetalleService.registrarDetalle();
                    break;
                case 2:
                    DetalleService.eliminarDetalle();
                    break;
                case 3:
                    DetalleService.consultarDetalles();
                    break;
                case 4:
                    DetalleService.editarDetalle();
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
