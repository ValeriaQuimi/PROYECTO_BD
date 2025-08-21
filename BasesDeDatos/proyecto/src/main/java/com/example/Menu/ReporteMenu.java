package com.example.Menu;

import java.util.Scanner;

import com.example.Service.Reporte;

public class ReporteMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void mostrarMenuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== MENÚ DE REPORTES ===");
            System.out.println("1. Categorías más vendidas");
            System.out.println("0. Salir");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    Reporte.mostrarReporteCategoriasMasVendidas();
                    break;
                case 0:
                    volver = true;
                    System.out.println("Saliendo del menú...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
