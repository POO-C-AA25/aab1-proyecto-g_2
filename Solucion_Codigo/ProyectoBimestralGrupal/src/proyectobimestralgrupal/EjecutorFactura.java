package proyectobimestralgrupal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EjecutorFactura {
    public Scanner entrada = new Scanner(System.in);
    public List<Producto> listaProductos = new ArrayList<>();
    public List<Factura> listaFacturas = new ArrayList<>();
    public CarritoDeCompras carrito = new CarritoDeCompras();
    public DatosFactura cliente = null;

    public static void main(String[] args) {
        EjecutorFactura app = new EjecutorFactura();
        app.cargarProductos();
        app.menu();
    }

    public void cargarProductos() {
        listaProductos.add(new Producto(1, "Arroz Blanco", 25, 6, 2025, 1.00, Categoria.ALIMENTACION, 100));
        listaProductos.add(new Producto(2, "Huevos Medianos", 2, 7, 2025, 2.50, Categoria.ALIMENTACION, 80));
        listaProductos.add(new Producto(3, "Paracetamol 500mg", 10, 8, 2025, 1.50, Categoria.SALUD, 60));
        listaProductos.add(new Producto(4, "Alcohol Antiseptico", 15, 12, 2025, 1.20, Categoria.SALUD, 90));
        listaProductos.add(new Producto(5, "Jeans Clasicos", 5, 11, 2025, 18.00, Categoria.VESTIMENTA, 30));
        listaProductos.add(new Producto(6, "Lapiz de Grafito", 10, 10, 2025, 0.30, Categoria.EDUCACION, 200));
        listaProductos.add(new Producto(7, "Cuaderno Escolar", 28, 9, 2025, 2.00, Categoria.EDUCACION, 100));
        listaProductos.add(new Producto(8, "Jabon de Tocador", 18, 7, 2025, 0.80, Categoria.VIVIENDA, 150));
        listaProductos.add(new Producto(9, "Leche Entera", 4, 7, 2025, 1.10, Categoria.ALIMENTACION, 60));
        listaProductos.add(new Producto(10, "Fosforos", 22, 10, 2025, 0.40, Categoria.VIVIENDA, 90));
    }

    public void menu() {
        int opcionPrincipal;
        int opcionClases;
        do {
            System.out.println("---------------------------");
            System.out.println("|        SUPERMAXI        |");
            System.out.println("---------------------------");
            System.out.println("[1] CREAR FACTURA");
            System.out.println("[2] AGREGAR/GESTIONAR PRODUCTOS");
            System.out.println("[3] MOSTRAR FACTURAS");
            System.out.println("[4] MOSTRAR ESTADISTICAS");
            System.out.println("[0] Salir");
            opcionPrincipal = entrada.nextInt();
            switch (opcionPrincipal) {
                case 1:
                    do {
                        System.out.println("[1] DATOS CLIENTE");
                        System.out.println("[2] COMPRAR PRODUCTOS");
                        System.out.println("[3] GENERAR FACTURA");
                        System.out.println("[0] Volver");
                        opcionClases = entrada.nextInt();
                        entrada.nextLine();
                        switch (opcionClases) {
                            case 1:
                                cliente = pedirDatosCliente();
                                break;
                            case 2:
                                agregarProductoACarrito();
                                break;
                            case 3:
                                if (cliente == null) {
                                    System.out.println("Primero ingrese los datos del cliente.");
                                } else if (carrito.productosCarrito.isEmpty()) {
                                    System.out.println("El carrito esta vacio.");
                                } else {
                                    Factura nueva = Factura.crearFactura(cliente, carrito.productosCarrito);
                                    listaFacturas.add(nueva);
                                    System.out.println(nueva);
                                    carrito.limpiarCarrito();
                                }
                                break;
                        }
                    } while (opcionClases != 0);
                    break;
                case 2:
                    do {
                        System.out.println("[1] AGREGAR PRODUCTOS NUEVOS A STOCK");
                        System.out.println("[2] ELIMINAR PRODUCTOS");
                        System.out.println("[3] MOSTRAR PRODUCTOS");
                        System.out.println("[0] Volver");
                        opcionClases = entrada.nextInt();
                        entrada.nextLine();
                        switch (opcionClases) {
                            case 1:
                                Producto nuevo = pedirProductoNuevo();
                                if (nuevo != null) listaProductos.add(nuevo);
                                break;
                            case 2:
                                eliminarProductoDeLista();
                                break;
                            case 3:
                                mostrarListaProductos();
                                break;
                        }
                    } while (opcionClases != 0);
                    break;
                case 3:
                    mostrarFacturas();
                    break;
                case 4:
                    mostrarEstadisticas();
                    guardarEstadisticasEnArchivo();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcionPrincipal != 0);
    }

    public DatosFactura pedirDatosCliente() {
        System.out.println("Ingrese nombre del cliente:");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese cedula del cliente:");
        String cedula = entrada.nextLine();
        System.out.println("Ingrese direccion del cliente:");
        String direccion = entrada.nextLine();
        System.out.println("Ingrese telefono del cliente:");
        String telefono = entrada.nextLine();
        return new DatosFactura(nombre, cedula, direccion, telefono);
    }

    public Producto pedirProductoNuevo() {
        System.out.println("Ingrese el nombre del producto:");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese fecha de caducidad:");
        System.out.print("Dia: ");
        int dia = entrada.nextInt();
        System.out.print("Mes: ");
        int mes = entrada.nextInt();
        System.out.print("Anio: ");
        int anio = entrada.nextInt();
        System.out.println("Ingrese el precio normal:");
        double precioNormal = entrada.nextDouble();
        entrada.nextLine();
        System.out.println("Ingrese la categoria (VIVIENDA, EDUCACION, ALIMENTACION, VESTIMENTA o SALUD):");
        String categoriaTexto = entrada.nextLine().toUpperCase();
        Categoria categoria;
        try {
            categoria = Categoria.valueOf(categoriaTexto);
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria invalida. Intente nuevamente.");
            return null;
        }
        System.out.println("Ingrese cuanto stock hay:");
        int stock = entrada.nextInt();
        entrada.nextLine();
        int codigo = listaProductos.isEmpty() ? 1 : listaProductos.get(listaProductos.size() - 1).codigo + 1;
        return new Producto(codigo, nombre, dia, mes, anio, precioNormal, categoria, stock);
    }

    public void agregarProductoACarrito() {
        mostrarListaProductos();
        System.out.print("Ingrese el codigo del producto que desea agregar al carrito: ");
        int codigoAgregar = entrada.nextInt();
        Producto productoEncontrado = null;
        for (Producto p : listaProductos) {
            if (p.codigo == codigoAgregar) {
                productoEncontrado = p;
                break;
            }
        }
        if (productoEncontrado == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.print("Ingrese la cantidad que desea agregar al carrito: ");
            int cantidad = entrada.nextInt();
            entrada.nextLine();
            if (cantidad > productoEncontrado.stock) {
                System.out.println("No hay suficiente stock disponible.");
            } else {
                carrito.agregarProducto(productoEncontrado, cantidad);
                System.out.println("Producto agregado al carrito correctamente.");
            }
        }
    }

    public void eliminarProductoDeLista() {
        mostrarListaProductos();
        System.out.print("Ingrese el codigo del producto a eliminar: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();
        Producto productoAEliminar = null;
        for (Producto p : listaProductos) {
            if (p.codigo == codigo) {
                productoAEliminar = p;
                break;
            }
        }
        if (productoAEliminar != null) {
            listaProductos.remove(productoAEliminar);
            System.out.println("Producto eliminado con exito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void mostrarListaProductos() {
        System.out.println("=== Lista de productos ===");
        int contador = 1;
        for (Producto p : listaProductos) {
            System.out.println(contador + ". " + p);
            contador++;
        }
    }

    public void mostrarFacturas() {
        if (listaFacturas.isEmpty()) {
            System.out.println("No existen facturas generadas en este momento.");
        } else {
            System.out.println("=== FACTURAS DEL DIA ===");
            int contador = 1;
            for (Factura f : listaFacturas) {
                System.out.println("Factura #" + contador);
                System.out.println(f);
                contador++;
            }
        }
    }

    public void mostrarEstadisticas() {
        double[] totalesCat = new double[5];
        double totalDia = 0;
        List<List<Producto>> productosVendidosPorCat = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            productosVendidosPorCat.add(new ArrayList<>());
        }
        for (Factura f : listaFacturas) {
            totalDia += f.total;
            for (Producto p : f.productosComprados) {
                int idx = p.categoria.ordinal();
                productosVendidosPorCat.get(idx).add(p);
                totalesCat[idx] += p.obtenerPrecioAplicado() * p.stock;
            }
        }
        System.out.println("=== ESTADISTICAS DEL DIA ===");
        System.out.println("Total vendido: $" + String.format("%.2f", totalDia));
        Categoria[] cats = Categoria.values();
        for (int i = 0; i < cats.length; i++) {
            System.out.println(cats[i] + ":");
            double totalCat = 0;
            for (Producto p : productosVendidosPorCat.get(i)) {
                double subtotalProd = p.obtenerPrecioAplicado() * p.stock;
                System.out.println("   - " + p.nombre + ": $" + String.format("%.2f", subtotalProd));
                totalCat += subtotalProd;
            }
            System.out.println("   Total " + cats[i] + ": $" + String.format("%.2f", totalCat));
        }
    }

    public void guardarEstadisticasEnArchivo() {
        try (FileWriter fw = new FileWriter("estadisticas.txt", false);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("=== ESTADISTICAS DEL DIA ===");
            double[] totalesCat = new double[5];
            double totalDia = 0;
            List<List<Producto>> productosVendidosPorCat = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                productosVendidosPorCat.add(new ArrayList<>());
            }
            for (Factura f : listaFacturas) {
                totalDia += f.total;
                for (Producto p : f.productosComprados) {
                    int idx = p.categoria.ordinal();
                    productosVendidosPorCat.get(idx).add(p);
                    totalesCat[idx] += p.obtenerPrecioAplicado() * p.stock;
                }
            }
            pw.println("Total vendido: $" + String.format("%.2f", totalDia));
            Categoria[] cats = Categoria.values();
            for (int i = 0; i < cats.length; i++) {
                pw.println(cats[i] + ":");
                double totalCat = 0;
                for (Producto p : productosVendidosPorCat.get(i)) {
                    double subtotalProd = p.obtenerPrecioAplicado() * p.stock;
                    pw.println("   - " + p.nombre + ": $" + String.format("%.2f", subtotalProd));
                    totalCat += subtotalProd;
                }
                pw.println("   Total " + cats[i] + ": $" + String.format("%.2f", totalCat));
            }
            pw.println("===============================");
        } catch (IOException e) {
            System.out.println("Error al guardar las estadisticas: " + e.getMessage());
        }
    }
}

