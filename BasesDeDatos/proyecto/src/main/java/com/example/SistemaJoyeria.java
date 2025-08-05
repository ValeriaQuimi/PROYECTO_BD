package com.example;

import java.util.Scanner;

import com.example.Service.CategoriaService;
import com.example.Service.ClienteService;
import com.example.Service.PagoService;
import com.example.Service.PedidoService;
import com.example.Service.ProductoService;

public class SistemaJoyeria {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            Menu.mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer de entrada

            switch (opcion) {
                case 1:
                    CategoriaService.gestionCategorias();
                case 2:
                    ProductoService.gestionProductos();
                    break;
                case 3:
                    ClienteService.gestionClientes();
                    break;
                case 4:
                    PedidoService.gestionPedidos();
                    break;
                case 5:
                    PagoService.gestionPagos();
                    break;

                case 0:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
