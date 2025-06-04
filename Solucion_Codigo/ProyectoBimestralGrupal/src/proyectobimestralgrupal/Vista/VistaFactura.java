package proyectobimestralgrupal.Vista;

import proyectobimestralgrupal.Modelo.Producto;
import proyectobimestralgrupal.Modelo.Factura;
import java.util.List;
import java.util.Scanner;

public class VistaFactura {
    public Scanner entrada = new Scanner(System.in);

    public int mostrarMenuPrincipal() {
        System.out.println("---------------------------");
        System.out.println("|        SUPERMAXI        |");
        System.out.println("---------------------------");
        System.out.println("[1] CREAR FACTURA");
        System.out.println("[2] AGREGAR/GESTIONAR PRODUCTOS");
        System.out.println("[3] MOSTRAR FACTURAS");
        System.out.println("[4] MOSTRAR ESTADISTICAS");
        System.out.println("[0] Salir");
        return entrada.nextInt();
    }

    public int mostrarMenuFactura() {
        System.out.println("[1] DATOS CLIENTE");
        System.out.println("[2] COMPRAR PRODUCTOS");
        System.out.println("[3] GENERAR FACTURA");
        System.out.println("[0] Volver");
        return entrada.nextInt();
    }

    public int mostrarMenuProducto() {
        System.out.println("[1] AGREGAR PRODUCTOS NUEVOS A STOCK");
        System.out.println("[2] ELIMINAR PRODUCTOS");
        System.out.println("[3] MOSTRAR PRODUCTOS");
        System.out.println("[0] Volver");
        return entrada.nextInt();
    }

    public void mostrarListaProductos(List<Producto> listaProductos) {
        System.out.println("---------- Lista de productos ----------");
        int contador = 1;
        for (Producto p : listaProductos) {
            System.out.println(contador + ". " + p);
            contador++;
        }
    }

    public void mostrarFacturas(List<Factura> listaFacturas) {
        if (listaFacturas.isEmpty()) {
            System.out.println("No existen facturas generadas en este momento.");
        } else {
            System.out.println("------- FACTURAS DEL DIA --------");
            int contador = 1;
            for (Factura f : listaFacturas) {
                System.out.println("Factura #" + contador);
                System.out.println(f);
                contador++;
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}