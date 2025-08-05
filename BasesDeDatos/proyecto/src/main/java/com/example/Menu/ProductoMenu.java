package com.example.Menu;

import java.util.Scanner;

import com.example.Service.ProductoService;

public class ProductoMenu {
 
    
    private static Scanner sc = new Scanner(System.in);

    // MENÚ Gestión de Productos
    public static void gestionProductos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Insertar nuevo producto");
            System.out.println("2. Actualizar información de producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Consultar productos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    ProductoService.registrarProducto();
                    break;
                case 2:
                    ProductoService.actualizarProducto();
                    break;
                case 3:
                    ProductoService.eliminarProducto();
                    break;
                case 4:
                    ProductoService.consultarProductos();
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
