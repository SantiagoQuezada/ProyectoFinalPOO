package Logico;

public class Empleado {
    private String idEmpleado;
    private String nombre;
    private String cargo;

    public Empleado(String idEmpleado, String nombre, String cargo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.cargo = cargo;
    }

    public String getIdEmpleado() { return idEmpleado; }
    public String getNombre() { return nombre; }
    public String getCargo() { return cargo; }
    
    public void setCargo(String cargo) { this.cargo = cargo; }
}