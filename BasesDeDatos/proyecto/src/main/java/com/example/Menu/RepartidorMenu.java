package com.example.Menu;

import java.util.Scanner;
import com.example.Service.RepartidorService;

public class RepartidorMenu {


    private static Scanner sc = new Scanner(System.in);

    // MENÚ Gestión de Productos
    public static void gestionRepartidores() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE REPARTIDORES ---");
            System.out.println("1. Registar nuevo repartidor");
            System.out.println("2. Actualizar información de repartidor");
            System.out.println("3. Eliminar repartidor");
            System.out.println("4. Consultar repartidores");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    RepartidorService.registrarRepartidor();
                    break;
                case 2:
                    RepartidorService.actualizarRepartidor();
                    break;
                case 3:
                    RepartidorService.eliminarRepartidor();
                    break;
                case 4:
                    RepartidorService.consultarRepartidores();
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
