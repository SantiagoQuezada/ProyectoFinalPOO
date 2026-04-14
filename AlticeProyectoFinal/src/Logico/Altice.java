package Logico;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.Date;

public class Altice implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Cliente> misClientes;

	private ArrayList<Plan> misPlanes;

	private ArrayList<Empleado> misEmpleados;

	private ArrayList<Pago> misPagos;

	private int contadorClientes = 1;

	private int contadorEmpleados = 1;

	private int contadorPagos = 1;

	private int contadorPlanes = 1;

	private static Altice altice = null;

	private Altice() {

		misClientes = new ArrayList<>();

		misPlanes = new ArrayList<>();

		misEmpleados = new ArrayList<>();

		misPagos = new ArrayList<>();

	}

	public static Altice getInstance() {

		if (altice == null) {

			altice = cargarDatos();

			if (altice == null) {

				altice = new Altice();

				altice.cargarDatosPorDefecto();

			}

		}

		return altice;

	}

	private static Altice cargarDatos() {

		Altice instancia = null;

		try {

			FileInputStream fileIn = new FileInputStream("AlticeData.dat");

			ObjectInputStream in = new ObjectInputStream(fileIn);

			instancia = (Altice) in.readObject();

			in.close();

			fileIn.close();

			System.out.println("✅ Datos cargados correctamente de AlticeData.dat");

		} catch (Exception e) {

			System.out.println("⚠️ Archivo corrupto o no encontrado. Creando datos por defecto.");

		}

		return instancia;

	}

	public static void guardarDatos() {

		if (altice == null)
			return;

		try {

			FileOutputStream fileOut = new FileOutputStream("AlticeData.dat");

			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(altice);

			out.close();

			fileOut.close();

			System.out.println("💾 Datos guardados exitosamente.");

		} catch (Exception e) {

			System.err.println("❌ ERROR GRAVE AL GUARDAR LOS DATOS: " + e.getMessage());

			e.printStackTrace();

		}

	}

	private void cargarDatosPorDefecto() {

		Usuario adminUser = new Usuario("admin", "1234", Rol.GERENTE);

		misEmpleados.add(new Empleado("000-0000000-0", "Administrador Principal", "809-000-0000", "Altice Central",
				generarIdEmpleado(), "Gerencia", 50000.0f, adminUser, "Activo"));

	}

	public String generarIdCliente() {

		String id = "C-" + String.format("%03d", contadorClientes);

		contadorClientes++;

		return id;

	}

	public String generarIdEmpleado() {

		String id = "E-" + String.format("%03d", contadorEmpleados);

		contadorEmpleados++;

		return id;

	}

	public String generarIdPago() {

		String id = "PAG-" + String.format("%04d", contadorPagos);

		contadorPagos++;

		return id;

	}

	public String generarIdPlan() {

		String id = "P-" + String.format("%02d", contadorPlanes);

		contadorPlanes++;

		return id;

	}

	public Empleado getEmpleadoById(String id) {

		for (Empleado e : misEmpleados) {

			if (e.getIdEmpleado().equals(id)) {

				return e;

			}

		}

		return null;

	}

	public Cliente getClienteById(String id) {

		for (Cliente c : misClientes) {

			if (c.getIdCliente().equals(id)) {

				return c;

			}

		}

		return null;

	}

	public Plan getPlanById(String id) {

		for (Plan p : misPlanes) {

			if (p.getIdPlan().equals(id)) {

				return p;

			}

		}

		return null;

	}

	public ArrayList<Empleado> getEmpleados() {

		return misEmpleados;

	}

	public void registrarEmpleado(Empleado e) {

		misEmpleados.add(e);

	}

	public void eliminarEmpleado(String id) {

		Empleado e = getEmpleadoById(id);

		if (e != null) {

			e.setEstado("Inactivo");

		}

	}

	public void registrarPago(Pago p) {

		misPagos.add(p);

	}

	public ArrayList<Pago> getPagos() {

		return misPagos;

	}

	public ArrayList<Cliente> getClientes() {

		return misClientes;

	}

	public void registrarCliente(Cliente c) {

		misClientes.add(c);

	}

	public void eliminarCliente(String id) {

		Cliente c = getClienteById(id);

		if (c != null) {

			c.setEstado("Inactivo");

		}

	}

	public ArrayList<Plan> getPlanes() {

		return misPlanes;

	}

	public void registrarPlan(Plan p) {

		misPlanes.add(p);

	}

	public void eliminarPlan(String idPlan) {

		Plan p = getPlanById(idPlan);

		if (p != null) {

			p.setEstado("Desactivado");

		}

	}

	public String[] obtenerNombresPlanesPorCategoria(String categoria) {

		ArrayList<String> nombres = new ArrayList<>();

		nombres.add("Seleccione un plan...");

		for (Plan p : misPlanes) {

			if (p.getCategoria().equals(categoria) && p.getEstado().equals("Activo")) {

				nombres.add(p.getNombre());

			}

		}

		return nombres.toArray(new String[0]);

	}

	public String[] obtenerNombresClientes() {

		ArrayList<String> nombres = new ArrayList<>();

		nombres.add("Seleccione un cliente...");

		for (Cliente c : misClientes) {

			nombres.add(c.getIdCliente() + " - " + c.getNombre());

		}

		return nombres.toArray(new String[0]);

	}

	public void asignarPlanACliente(String idCliente, String nombrePlan) {

		Plan planAsignar = null;

		for (Plan p : misPlanes) {

			if (p.getNombre().equals(nombrePlan)) {

				planAsignar = p;

				break;

			}

		}

		if (planAsignar != null) {

			for (Cliente c : misClientes) {

				if (c.getIdCliente().equals(idCliente)) {

					c.setPlan(planAsignar);

					c.setFechaAsignacionPlan(new Date());

					break;

				}

			}

		}

	}

	public Empleado validarLogin(String username, String password) {

		for (Empleado e : misEmpleados) {

			if (e.getUsuario() != null) {

				if (e.getUsuario().getUsername().equals(username) && e.getUsuario().getPassword().equals(password)) {

					return e;

				}

			}

		}

		return null;

	}

}