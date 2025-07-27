package com.example.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.Entidades.Producto;
import com.example.enums.CategoriaProducto;
import com.example.enums.EstadoProducto;
import com.example.enums.TipoProducto;

public class ProductoService {

    private static Scanner sc = new Scanner(System.in);
    public static List<Producto> productos = new ArrayList<>();

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
                    registrarProducto();
                    break;
                case 2:
                    actualizarProducto();
                    break;
                case 3:
                    eliminarProducto();
                    break;
                case 4:
                    consultarProductos();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void registrarProducto() {
        System.out.println("\n=== REGISTRO DE PRODUCTO ===");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());
        System.out.println("Cantidad Disponible: ");
        int cantidadDisponible = Integer.parseInt(sc.nextLine());
        System.out.print("Stock Minimo: ");
        int stockMinimo = Integer.parseInt(sc.nextLine());
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();
        System.out.print("Elija una categoría: ");
        for (CategoriaProducto catProducto : CategoriaProducto.values()) {
            System.out.println("- " + catProducto);
        }
        String entrada = sc.nextLine();
        CategoriaProducto categoria = CategoriaProducto.valueOf(entrada.toUpperCase());
        System.out.println("Elija un estado");
        for (EstadoProducto estProducto : EstadoProducto.values()) {
            System.out.println("- " + estProducto);
        }
        String entrada2 = sc.nextLine();
        EstadoProducto estado = EstadoProducto.valueOf(entrada2.toUpperCase());
        System.out.println("Elija un tipo: ");
        for(TipoProducto tipoProducto: TipoProducto.values()){
            System.out.println("- " + tipoProducto);
        }
         String entrada3 = sc.nextLine();
         TipoProducto tipo= TipoProducto.valueOf(entrada3.toUpperCase());
        productos
                .add(new Producto(nombre, descripcion, precio, cantidadDisponible, stockMinimo, categoria, estado, tipo));
        System.out.println("Producto registrado.");
    }

    public static void actualizarProducto() {
        System.out.print("\nIngrese el código del producto a actualizar: ");
        String codigo = sc.nextLine();

        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto != null) {
            System.out.println("Actualizando: " + producto.getNombre());

            // Leer el nuevo precio
            System.out.print("Nuevo precio: ");
            producto.setPrecio(sc.nextDouble());

            // Limpiar el buffer después de nextDouble()
            sc.nextLine(); // Consumir el salto de línea pendiente

            // Leer la nueva descripción
            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine();
            producto.setDescripcion(descripcion);

            System.out.println("Producto actualizado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Eliminar Producto
    public static void eliminarProducto() {
        System.out.print("\nIngrese el código del producto a eliminar: ");
        String codigo = sc.nextLine();

        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto != null) {
            productos.remove(producto);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Consultar Productos
    public static void consultarProductos() {
        System.out.println("\n--- Listado de Productos ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            for (Producto producto : productos) {
                System.out.println(producto);
            }
        }
    }

    // Buscar Producto por Código
    public static Producto buscarProductoPorCodigo(String codigo) {
        int numID = Integer.parseInt(codigo);
        for (Producto producto : productos) {
            if (producto.getIdProducto() == numID) {
                return producto;
            }
        }
        return null; // No encontrado
    }
}