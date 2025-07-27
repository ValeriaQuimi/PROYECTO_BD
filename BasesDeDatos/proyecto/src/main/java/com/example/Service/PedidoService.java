package com.example.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.example.Entidades.Cliente;
import com.example.Entidades.Pedido;
import com.example.Entidades.Producto;
import com.example.enums.EstadoPedido;
import com.example.enums.TipoEntrega;

public class PedidoService {

    private static Scanner sc = new Scanner(System.in);
    private static List<Pedido> pedidos = new ArrayList<>();

    public static void gestionPedidos() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ DE PEDIDOS ===");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Despachar por pedido");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    crearPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    despacharPedidoPorId();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo del menú...");
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        }
    }

    public static void crearPedido() {
        System.out.println("\n=== CREAR PEDIDO ===");

        // 👉 Buscar cliente
        System.out.print("Ingrese ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());
        Cliente cliente = ClienteService.buscarClientePorId(String.valueOf(idCliente));
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        // 👉 Seleccionar productos
        List<Producto> productosSeleccionados = new ArrayList<>();
        int montoTotal = 0;
        boolean agregarMas;
        do {
            System.out.print("Ingrese ID del producto a agregar: ");
            int idProd = Integer.parseInt(sc.nextLine());
            Producto producto = ProductoService.buscarProductoPorCodigo(String.valueOf(idProd));
            if (producto != null) {
                productosSeleccionados.add(producto);
                montoTotal += producto.getPrecio();
                System.out.println("Producto agregado.");
            } else {
                System.out.println("Producto no encontrado.");
            }

            System.out.print("¿Desea agregar otro producto? (s/n): ");
            agregarMas = sc.nextLine().equalsIgnoreCase("s");
        } while (agregarMas);

        System.out.print("Fecha entrega (dd/MM/yyyy): ");
        String fechaStr = sc.nextLine();
        Date fechaEntrega;
        try {
            fechaEntrega = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        } catch (ParseException e) {
            System.out.println("Fecha inválida.");
            return;
        }

        System.out.print("Tipo de entrega (DOMICILIO, TIENDA): ");
        TipoEntrega tipoEntrega;
        try {
            tipoEntrega = TipoEntrega.valueOf(sc.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de entrega inválido.");
            return;
        }

        System.out.print("Estado del pedido (ENTREGADO, PENDIENTE, PAGADO, CANCELADO): ");
        EstadoPedido estado;
        try {
            estado = EstadoPedido.valueOf(sc.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido.");
            return;
        }

        Pedido pedido = new Pedido(cliente, productosSeleccionados, montoTotal, fechaEntrega, tipoEntrega, estado);
        pedidos.add(pedido);
        System.out.println(" Pedido creado con número: " + pedido.getNumOrden());
    }

    public static void listarPedidos() {
        System.out.println("\n=== LISTADO DE PEDIDOS ===");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println("Número: " + p.getNumOrden() +
                    ", Monto: " + p.getMontoTotal() +
                    ", Fecha Entrega: " + new SimpleDateFormat("dd/MM/yyyy").format(p.getFechaEntrega()) +
                    ", Tipo Entrega: " + p.getTipoEntrega() +
                    ", Estado: " + p.getEstado());
        }
    }

    public static Pedido buscarPedidoPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getNumOrden() == id) {
                return p;
            }
        }
        return null;
    }

    public static void despacharPedidoPorId() {
        System.out.print("Ingrese número de pedido a despachar: ");
        int idPedido = Integer.parseInt(sc.nextLine());

        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        // Aquí llamamos al método que hace la verificación y el cambio de estado
        PedidoService servicio = new PedidoService();
        servicio.despacharPedido(pedido);
    }

    public void despacharPedido(Pedido pedido) {
        Cliente cliente = pedido.getCliente();

        if (pedido.getEstado() != EstadoPedido.PAGADO) {
            System.out.println("El pedido no puede ser despachado, el pago no está completo.");
            return;
        }

        if (cliente.getDireccion() == null || cliente.getDireccion().isEmpty()) {
            System.out.println("Datos del cliente incompletos, no se puede despachar.");
            return;
        }

        pedido.setEstado(EstadoPedido.ENTREGADO);
        System.out.println("Pedido despachado correctamente.");
    }

}
