package Logico;

public class Cliente extends Persona {
    private String idCliente;
    private String estado;
    private Plan plan;

    public Cliente(String cedula, String nombre, String telefono, String direccion, String idCliente, String estado, Plan plan) {
        super(cedula, nombre, telefono, direccion);
        this.idCliente = idCliente;
        this.estado = estado;
        this.plan = plan;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}