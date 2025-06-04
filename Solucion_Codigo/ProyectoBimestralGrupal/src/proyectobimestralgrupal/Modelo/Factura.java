package proyectobimestralgrupal.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Factura {
    public DatosFactura datosCliente;
    public List<Producto> productosComprados;
    public double total;
    public double iva;
    public double[] totalesPorCategoria = new double[5];

    public Factura() {}

    public static Factura crearFactura(DatosFactura cliente, List<Producto> productos) {
        Factura f = new Factura();
        f.datosCliente = cliente;
        f.productosComprados = new ArrayList<>();
        double suma = 0;
        double[] cat = new double[5];
        for (Producto p : productos) {
            double precio = p.obtenerPrecioAplicado();
            suma += precio * p.stock;
            cat[p.categoria.ordinal()] += precio * p.stock;
            f.productosComprados.add(p);
        }
        f.iva = suma * 0.15;
        f.total = suma + f.iva;
        f.totalesPorCategoria = cat;
        return f;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------ FACTURA ------------\n");
        sb.append(datosCliente).append("\n");
        sb.append("Productos:\n");
        for (Producto p : productosComprados) {
            sb.append(p.nombre)
              .append(p.categoria)
              .append(" x").append(p.stock)
              .append(" $").append(String.format("%.2f", p.obtenerPrecioAplicado()));
            if (p.esPrecioPromo()) sb.append(" (PROMO)");
            sb.append("\n");
        }
        sb.append("\nCategoria por productos:\n");
        Categoria[] cats = Categoria.values();
        for (int i = 0; i < cats.length; i++) {
            double totalCat = 0;
            sb.append(cats[i]).append(":\n");
            for (Producto p : productosComprados) {
                if (p.categoria == cats[i]) {
                    double subtotalProd = p.obtenerPrecioAplicado() * p.stock;
                    sb.append("  ").append(p.nombre)
                      .append(": $").append(String.format("%.2f", subtotalProd)).append("\n");
                    totalCat += subtotalProd;
                }
            }
            sb.append("   Total ").append(cats[i]).append(": $").append(String.format("%.2f", totalCat)).append("\n");
        }
        sb.append("Subtotal: $").append(String.format("%.2f", total - iva)).append("\n");
        sb.append("IVA (15%): $").append(String.format("%.2f", iva)).append("\n");
        sb.append("Total: $").append(String.format("%.2f", total)).append("\n");
        sb.append("--------------------\n");
        return sb.toString();
    }
}