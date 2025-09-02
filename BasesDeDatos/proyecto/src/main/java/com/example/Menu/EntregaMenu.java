package com.example.Menu;

import java.util.Scanner;

import com.example.Service.EntregaService;

public class EntregaMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void gestionEntregas() {

        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n=== GESTIÓN DE ENTREGAS ===");
            System.out.println("1. Registrar Entrega");
            System.out.println("2. Actualizar Entrega");
            System.out.println("3. Eliminar Entrega");
            System.out.println("4. Consultar Entregas");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();
            
            switch (opcion) {
                case 1:
                    EntregaService.registrarEntrega();
                    break;
                case 2:
                    EntregaService.actualizarEntrega();
                    break;
                case 3:
                    EntregaService.eliminarEntrega();
                    break;
                case 4:
                    EntregaService.consultarEntregas();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
            
        }
    }
}
