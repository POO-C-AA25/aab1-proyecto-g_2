package proyectobimestralgrupal.Controlador;

import proyectobimestralgrupal.Modelo.*;
import proyectobimestralgrupal.Vista.VistaFactura;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ControladorFactura {
    
    
    public List<Producto> listaProductos = new ArrayList<>();
    public List<Factura> listaFacturas = new ArrayList<>();
    public CarritoDeCompras carrito = new CarritoDeCompras();
    public DatosFactura cliente = null;
    public VistaFactura vista = new VistaFactura();

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

    public void ejecutarMenu() {
        int opcionPrincipal;
        int opcionClases;
        cargarProductos();
        do {
            opcionPrincipal = vista.mostrarMenuPrincipal();
            switch (opcionPrincipal) {
                case 1:
                    do {
                        opcionClases = vista.mostrarMenuFactura();
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
                        opcionClases = vista.mostrarMenuProducto();
                        switch (opcionClases) {
                            case 1:
                                Producto nuevo = pedirProductoNuevo();
                                if (nuevo != null) listaProductos.add(nuevo);
                                break;
                            case 2:
                                eliminarProductoDeLista();
                                break;
                            case 3:
                                vista.mostrarListaProductos(listaProductos);
                                break;
                        }
                    } while (opcionClases != 0);
                    break;
                case 3:
                    vista.mostrarFacturas(listaFacturas);
                    break;
                case 4:
                    mostrarEstadisticas();
                    guardarEstadisticasEnArchivo();
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo del sistema...");
                    break;
                default:
                    vista.mostrarMensaje("Opcion no valida. Intente nuevamente.");
            }
        } while (opcionPrincipal != 0);
    }

    public DatosFactura pedirDatosCliente() {
        System.out.println("Ingrese nombre del cliente:");
        vista.entrada.nextLine();
        String nombre = vista.entrada.nextLine();
        System.out.println("Ingrese cedula del cliente:");
        String cedula = vista.entrada.nextLine();
        System.out.println("Ingrese direccion del cliente:");
        String direccion = vista.entrada.nextLine();
        System.out.println("Ingrese telefono del cliente:");
        String telefono = vista.entrada.nextLine();
        return new DatosFactura(nombre, cedula, direccion, telefono);
    }

    public Producto pedirProductoNuevo() {
        System.out.println("Ingrese el nombre del producto:");
        vista.entrada.nextLine();
        String nombre = vista.entrada.nextLine();
        System.out.println("Ingrese fecha de caducidad:");
        System.out.print("Dia: ");
        int dia = vista.entrada.nextInt();
        System.out.print("Mes: ");
        int mes = vista.entrada.nextInt();
        System.out.print("Anio: ");
        int anio = vista.entrada.nextInt();
        System.out.println("Ingrese el precio normal:");
        double precioNormal = vista.entrada.nextDouble();
        vista.entrada.nextLine();
        System.out.println("Ingrese la categoria (VIVIENDA, EDUCACION, ALIMENTACION, VESTIMENTA o SALUD):");
        String categoriaTexto = vista.entrada.nextLine().toUpperCase();
        Categoria categoria;
        try {
            categoria = Categoria.valueOf(categoriaTexto);
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria invalida. Intente nuevamente.");
            return null;
        }
        System.out.println("Ingrese cuanto stock hay:");
        int stock = vista.entrada.nextInt();
        vista.entrada.nextLine();
        int codigo = listaProductos.isEmpty() ? 1 : listaProductos.get(listaProductos.size() - 1).codigo + 1;
        return new Producto(codigo, nombre, dia, mes, anio, precioNormal, categoria, stock);
    }

    public void agregarProductoACarrito() {
        vista.mostrarListaProductos(listaProductos);
        System.out.print("Ingrese el codigo del producto que desea agregar al carrito: ");
        int codigoAgregar = vista.entrada.nextInt();
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
            int cantidad = vista.entrada.nextInt();
            vista.entrada.nextLine();
            if (cantidad > productoEncontrado.stock) {
                System.out.println("No hay suficiente stock disponible.");
            } else {
                carrito.agregarProducto(productoEncontrado, cantidad);
                System.out.println("Producto agregado al carrito correctamente.");
            }
        }
    }

    public void eliminarProductoDeLista() {
        vista.mostrarListaProductos(listaProductos);
        System.out.print("Ingrese el codigo del producto a eliminar: ");
        int codigo = vista.entrada.nextInt();
        vista.entrada.nextLine();
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
