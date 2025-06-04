package proyectobimestralgrupal;
import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompras {
    public List<Producto> productosCarrito = new ArrayList<>();

    public CarritoDeCompras() {}

    public void agregarProducto(Producto producto, int cantidad) {
        Producto productoParaCarrito = new Producto(
            producto.codigo,
            producto.nombre,
            producto.diaCaduca,
            producto.mesCaduca,
            producto.anioCaduca,
            producto.precioNormal,
            producto.categoria,
            cantidad
        );
        productosCarrito.add(productoParaCarrito);
        producto.stock -= cantidad;
    }

    public void limpiarCarrito() {
        productosCarrito.clear();
    }
}





