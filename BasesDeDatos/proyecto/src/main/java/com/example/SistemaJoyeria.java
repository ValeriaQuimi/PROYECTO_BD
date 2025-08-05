package com.example;

import java.util.Scanner;

import com.example.Menu.CategoriaMenu;
import com.example.Menu.ClienteMenu;
import com.example.Menu.Menu;
import com.example.Menu.PagoMenu;
import com.example.Menu.ProductoMenu;
import com.example.Menu.RepartidorMenu;
import com.example.Service.PedidoService;

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
                    CategoriaMenu.gestionCategorias();
                    break;
                case 2:
                    ProductoMenu.gestionProductos();
                    break;
                case 3:
                    ClienteMenu.gestionClientes();
                    break;
                case 4:
                    PedidoService.gestionPedidos();
                    break;
                case 5:
                    PagoMenu.gestionPagos();
                    break;

                case 6:
                    RepartidorMenu.gestionRepartidores();
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
