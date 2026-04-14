package Logico;

import java.io.Serializable;
import java.util.Date;

public class Pago implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idPago;
	private Cliente cliente;
	private Date fecha;
	private float monto;
	private String metodoPago;
	private String concepto;

	public Pago(String idPago, Cliente cliente, Date fecha, float monto, String metodoPago, String concepto) {
		this.idPago = idPago;
		this.cliente = cliente;
		this.fecha = fecha;
		this.monto = monto;
		this.metodoPago = metodoPago;
		this.concepto = concepto;
	}

	public String getIdPago() {
		return idPago;
	}

	public void setIdPago(String idPago) {
		this.idPago = idPago;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
}