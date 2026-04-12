package Logico;

public class Empleado extends Persona {
    private String idEmpleado;
    private String departamento;
    private float salario;
    private Usuario usuario;

    public Empleado(String cedula, String nombre, String telefono, String direccion, String idEmpleado, String departamento, float salario, Usuario usuario) {
        super(cedula, nombre, telefono, direccion);
        this.idEmpleado = idEmpleado;
        this.departamento = departamento;
        this.salario = salario;
        this.usuario = usuario;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}