package com.example.Menu;

import java.util.Scanner;
import com.example.Service.EntregaService;

public class EntregaMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void gestionEntregas() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTION DE ENTREGAS ---");
            System.out.println("1. Registrar nueva entrega");
            System.out.println("2. Consultar entregas");
            System.out.println("3. Editar entrega");
            System.out.println("4. Eliminar entrega");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            String input = sc.nextLine();
            int opcion;

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    EntregaService.registrarEntrega();
                    break;
                case 2:
                    EntregaService.consultarEntregas();
                    break;
                case 3:
                    EntregaService.editarEntrega();
                    break;
                case 4:
                    EntregaService.eliminarEntrega();
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
