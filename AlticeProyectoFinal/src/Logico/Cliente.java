package Logico;

import java.util.Date;

public class Cliente extends Persona {
    private String idCliente;
    private String estado;
    private Plan plan;
    private Date fechaAsignacionPlan;
    private String tipoCliente;
    private String rnc;

    public Cliente(String cedula, String nombre, String telefono, String direccion, String idCliente, String estado, Plan plan, String tipoCliente, String rnc) {
        super(cedula, nombre, telefono, direccion);
        this.idCliente = idCliente;
        this.estado = estado;
        this.plan = plan;
        this.fechaAsignacionPlan = null;
        this.tipoCliente = tipoCliente;
        this.rnc = rnc;
    }

    @Override
    public String mostrarDetalles() {
        return "Detalles del Cliente:\n" +
               "ID: " + idCliente + "\n" +
               "Nombre/Empresa: " + getNombre() + "\n" +
               "Tipo: " + tipoCliente + "\n" +
               "Estado: " + estado;
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

    public Date getFechaAsignacionPlan() {
        return fechaAsignacionPlan;
    }

    public void setFechaAsignacionPlan(Date fechaAsignacionPlan) {
        this.fechaAsignacionPlan = fechaAsignacionPlan;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getRnc() {
        return rnc;
    }

    public void setRnc(String rnc) {
        this.rnc = rnc;
    }
}