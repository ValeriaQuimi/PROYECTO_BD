package com.example.Menu;

import java.util.Scanner;
import com.example.Service.ReporteService;

public class ReporteMenu {
    private static Scanner sc = new Scanner(System.in);

    public static void mostrarMenuReportes() {
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n=== MENÚ DE REPORTES ===");
            System.out.println("1. Categoría Más Vendida");
            System.out.println("2. Historial de Compras de Clientes");
            System.out.println("3. Pagos del Mes Actual");
            System.out.println("4. Entregas por Repartidor y Cliente");
            System.out.println("5. Productos con Stock Bajo");
            System.out.println("6. Clientes VIP");
            System.out.println("0. Volver al Menú Principal");
            
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine();
            
            switch (opcion) {
                case "1":
                    ReporteService.mostrarReporteCategoriaMasVendida();
                    break;
                case "2":
                    ReporteService.mostrarReporteHistorialCompras();
                    break;
                case "3":
                    ReporteService.mostrarReportePagosDelMes();
                    break;
                case "4":
                    ReporteService.mostrarReporteEntregasRepartidorCliente();
                    break;
                case "5":
                    ReporteService.mostrarReporteProductosStockBajo();
                    break;
                case "6":
                    ReporteService.mostrarReporteClientesVIP();
                    break;
                case "0":
                    continuar = false;
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción del 0 al 6.");
                    break;
            }
            
            if (continuar) {
                System.out.print("\nPresione Enter para continuar...");
                sc.nextLine();
            }
        }
    }
}
