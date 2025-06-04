package proyectobimestralgrupal;

public class Producto {
    public int codigo;
    public String nombre;
    public int diaCaduca;
    public int mesCaduca;
    public int anioCaduca;
    public double precioNormal;
    public Categoria categoria;
    public int stock;

    public Producto() {}

    public Producto(int codigo, String nombre, int dia, int mes, int anio,
                    double precioNormal, Categoria categoria, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.diaCaduca = dia;
        this.mesCaduca = mes;
        this.anioCaduca = anio;
        this.precioNormal = precioNormal;
        this.categoria = categoria;
        this.stock = stock;
    }

    public boolean esPrecioPromo() {
        return stock > 100 || estaProximaCaducidad();
    }

    public boolean estaProximaCaducidad() {
        int hoyDia = 4, hoyMes = 6, hoyAnio = 2025;
        if (anioCaduca < hoyAnio) return false;
        if (anioCaduca == hoyAnio) {
            if (mesCaduca == hoyMes && diaCaduca - hoyDia <= 30 && diaCaduca - hoyDia >= 0) return true;
            if (mesCaduca == hoyMes + 1 && diaCaduca + (30 - hoyDia) <= 30) return true;
        }
        return false;
    }

    public double obtenerPrecioAplicado() {
        if (esPrecioPromo()) {
            return precioNormal * 0.8;
        } else {
            return precioNormal;
        }
    }

    @Override
    public String toString() {
        return nombre + " x" + stock + " $" + precioNormal +
               " (Vence: " + diaCaduca + "/" + mesCaduca + "/" + anioCaduca + ")" +
               (esPrecioPromo() ? " [PROMO 20%]" : "");
    }
}



