package com.example.Menu;

import java.util.Scanner;

import com.example.Service.ClienteService;

public class ClienteMenu {

    
    private static Scanner sc = new Scanner(System.in);

    // Menu Gestión de Clientes
    public static void gestionClientes() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Actualizar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Consultar clientes");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    ClienteService.registrarCliente();
                    break;
                case 2:
                    ClienteService.actualizarCliente();
                    break;
                case 3:
                    ClienteService.eliminarCliente();
                    break;
                case 4:
                    ClienteService.consultarClientes();
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
