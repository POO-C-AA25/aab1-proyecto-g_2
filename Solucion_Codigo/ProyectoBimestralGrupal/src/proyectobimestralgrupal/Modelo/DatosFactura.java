package proyectobimestralgrupal.Modelo;


public class DatosFactura {
     public String nombre;
    public String cedula;
    public String direccion;
    public String telefono;

    public DatosFactura() {}

    public DatosFactura(String nombre, String cedula, String direccion, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cliente: " + nombre + "\n" +
                "Cedula: " + cedula + "\n" +
                "Direccion: " + direccion + "\n" +
                "Telefono: " + telefono;
    }
}





