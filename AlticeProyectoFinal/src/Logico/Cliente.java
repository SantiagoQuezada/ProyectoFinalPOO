package Logico;

import java.util.Date;
import java.util.Calendar;
import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idCliente;
    private String estado;
    private Plan plan;
    private Date fechaAsignacionPlan;
    private int mesesContrato;

    public Cliente(String cedula, String nombre, String telefono, String direccion, String idCliente, String estado, Plan plan) {
        super(cedula, nombre, telefono, direccion);
        this.idCliente = idCliente;
        this.estado = estado;
        this.plan = plan;
        this.fechaAsignacionPlan = null;
        this.mesesContrato = 0;
    }

    @Override
    public String mostrarDetalles() {
        String planContratado = (plan != null) ? plan.getNombre() : "Sin Plan";
        return "Cliente [ID=" + idCliente + ", Nombre=" + nombre + ", Estado=" + estado + ", Plan=" + planContratado + "]";
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

    public int getMesesContrato() {
        return mesesContrato;
    }

    public void setMesesContrato(int mesesContrato) {
        this.mesesContrato = mesesContrato;
    }

    public int getMesesRestantes() {
        if (fechaAsignacionPlan == null || mesesContrato <= 0) return 0;
        
        Calendar start = Calendar.getInstance();
        start.setTime(fechaAsignacionPlan);
        
        Calendar end = Calendar.getInstance();
        
        int diffYear = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        
        int restantes = mesesContrato - diffMonth;
        
        return Math.max(restantes, 0);
    }
}