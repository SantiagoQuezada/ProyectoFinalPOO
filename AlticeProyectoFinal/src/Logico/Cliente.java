package Logico;

public class Cliente {
	private String idCliente;
	private String cedula;
	private String nombre;
	private String telefono;
	private String direccion;
	private Plan plan;

	public Cliente(String idCliente, String cedula, String nombre, String telefono, String direccion, Plan plan) {
		this.idCliente = idCliente;
		this.cedula = cedula;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.plan = plan;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public String getCedula() {
		return cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
}