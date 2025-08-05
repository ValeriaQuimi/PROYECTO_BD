package com.example.Menu;

import java.util.Scanner;

import com.example.Service.CategoriaService;

public class CategoriaMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void gestionCategorias() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Registrar nueva categoría");
            System.out.println("2. Eliminar categoría");
            System.out.println("3. Consultar categorías");
            System.out.println("4. Editar categoria");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    CategoriaService.registrarCategoria();
                    break;
                case 2:
                    CategoriaService.eliminarCategoria();
                    break;
                case 3:
                    CategoriaService.consultarCategorias();
                    break;
                case 4:
                    CategoriaService.editarCategoria();
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
