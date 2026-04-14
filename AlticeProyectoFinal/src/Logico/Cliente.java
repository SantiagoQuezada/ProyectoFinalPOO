package Logico;

import java.io.Serializable;
import java.util.Date;

public class Cliente extends Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String idCliente;
	private String tipoCliente;
	private String rnc;
	private Plan plan;
	private Contrato contrato;
	private String estado;
	private float deudaActiva;
	private Date fechaAsignacionPlan; 

	public Cliente(String cedula, String nombre, String telefono, String direccion, String idCliente, String tipoCliente, String rnc, Plan plan, Contrato contrato, String estado) {
		super(cedula, nombre, telefono, direccion);
		this.idCliente = idCliente;
		this.tipoCliente = tipoCliente;
		this.rnc = rnc;
		this.plan = plan;
		this.contrato = contrato;
		this.estado = estado;
		this.deudaActiva = 0.0f;
	}


	public Cliente(String cedula, String nombre, String telefono, String direccion, String idCliente, String estado, Plan plan, String tipoCliente, String rnc) {
		super(cedula, nombre, telefono, direccion);
		this.idCliente = idCliente;
		this.estado = estado;
		this.plan = plan;
		this.tipoCliente = tipoCliente;
		this.rnc = rnc;
		this.deudaActiva = 0.0f;
	}

	@Override
	public String mostrarDetalles() {
		return "ID: " + idCliente + " | Nombre: " + getNombre() + " | Cédula/RNC: " + (rnc != null && !rnc.isEmpty() ? rnc : getCedula());
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
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

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public float getDeudaActiva() {
		return deudaActiva;
	}

	public void setDeudaActiva(float deudaActiva) {
		this.deudaActiva = deudaActiva;
	}
	
	public Date getFechaAsignacionPlan() {
		return fechaAsignacionPlan;
	}

	public void setFechaAsignacionPlan(Date fechaAsignacionPlan) {
		this.fechaAsignacionPlan = fechaAsignacionPlan;
	}

	public void agregarDeuda(float monto) {
		this.deudaActiva += monto;
	}

	public void reducirDeuda(float monto) {
		this.deudaActiva -= monto;
		if (this.deudaActiva < 0) {
			this.deudaActiva = 0;
		}
	}
}