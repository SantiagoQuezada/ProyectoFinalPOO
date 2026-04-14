package Logico;

import java.util.Date;
import java.io.Serializable;

public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;
	private String idContrato;
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;
	private Servicio servicio;
	private int duracionMeses;

	public Contrato(String idContrato, Date fechaInicio, Date fechaFin, String estado, Servicio servicio,
			int duracionMeses) {
		this.idContrato = idContrato;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.servicio = servicio;
		this.duracionMeses = duracionMeses;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public int getDuracionMeses() {
		return duracionMeses;
	}

	public void setDuracionMeses(int duracionMeses) {
		this.duracionMeses = duracionMeses;
	}
}