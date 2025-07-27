package com.example.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.Entidades.Cliente;
import com.example.enums.CategoriaCliente;

public class ClienteService {

    private static Scanner sc = new Scanner(System.in);
    private static List<Cliente> clientes = new ArrayList<>();

    // MENÚ Gestión de Clientes
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
                    registrarCliente();
                    break;
                case 2:
                    actualizarCliente();
                    break;
                case 3:
                    eliminarCliente();
                    break;
                case 4:
                    consultarClientes();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void registrarCliente() {
        System.out.println("\n=== REGISTRO DE CLIENTE ===");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Categoría (NORMAL/VIP): ");
        String entrada = sc.nextLine();
        CategoriaCliente categoria = CategoriaCliente.valueOf(entrada.toUpperCase());

        clientes.add(new Cliente(nombre, telefono, direccion, correo, categoria));
        System.out.println("Cliente registrado.");
    }

    // Actualizar información de un cliente
    public static void actualizarCliente() {
        System.out.print("\nIngrese el ID del cliente a actualizar: ");
        String id = sc.nextLine();

        // Buscar al cliente por su ID en la lista
        Cliente cliente = buscarClientePorId(id);
        if (cliente != null) {
            System.out.println("Actualizando: " + cliente.getNombre());
            System.out.print("Nuevo teléfono: ");
            cliente.setTelefono(sc.nextLine());
            System.out.print("Nueva dirección: ");
            cliente.setDireccion(sc.nextLine());
            System.out.println("Cliente actualizado.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // Eliminar un cliente
    public static void eliminarCliente() {
        System.out.print("\nIngrese el ID del cliente a eliminar: ");
        String id = sc.nextLine();

        // Buscar al cliente por su ID en la lista
        Cliente cliente = buscarClientePorId(id);
        if (cliente != null) {
            clientes.remove(cliente);
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // Consultar clientes
    public static void consultarClientes() {
        System.out.println("\n--- Listado de Clientes ---");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    // Método auxiliar para buscar un cliente por su ID
    public static Cliente buscarClientePorId(String id) {
        int numID = Integer.parseInt(id);
        for (Cliente cliente : clientes) {
            if (cliente.getId() == numID) {
                return cliente;
            }
        }
        return null; // No encontrado
    }
}
