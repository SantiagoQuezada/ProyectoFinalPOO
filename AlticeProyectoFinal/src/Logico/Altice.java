package Logico;

import java.util.ArrayList;
import java.util.Date;

public class Altice {
    private static Altice instance;
    private ArrayList<Empleado> misEmpleados;
    private ArrayList<Cliente> misClientes;
    private ArrayList<Plan> misPlanes;
    private ArrayList<Servicio> misServicios;
    private ArrayList<Contrato> misContratos;
    private ArrayList<Pagos> misPagos;
    private int contadorClientes;
    private int contadorEmpleados;
    private int contadorContratos;
    private int contadorPagos;
    private int contadorPlanes;

    private Altice() {
        misEmpleados = new ArrayList<>();
        misClientes = new ArrayList<>();
        misPlanes = new ArrayList<>();
        misServicios = new ArrayList<>();
        misContratos = new ArrayList<>();
        misPagos = new ArrayList<>();
        contadorClientes = 1;
        contadorEmpleados = 1001;
        contadorContratos = 1;
        contadorPagos = 1;
        contadorPlanes = 9;
        cargarDatosPrueba();
    }

    public static Altice getInstance() {
        if (instance == null) {
            instance = new Altice();
        }
        return instance;
    }

    private void cargarDatosPrueba() {
        misPlanes.add(new Plan("P-01", "Combinado", "Tripleplay Básico", 1500.0f));
        misPlanes.add(new Plan("P-02", "Combinado", "Tripleplay Premium", 2500.0f));
        misPlanes.add(new Plan("P-03", "Hogar", "Internet Fibra 100Mbps", 1200.0f));
        misPlanes.add(new Plan("P-04", "Hogar", "Internet Fibra 300Mbps", 1800.0f));
        misPlanes.add(new Plan("P-05", "Móvil", "Pospago Básico 15GB", 600.0f));
        misPlanes.add(new Plan("P-06", "Móvil", "Pospago Ilimitado 5G", 1100.0f));
        misPlanes.add(new Plan("P-07", "Empresarial", "Internet Dedicado 500Mbps", 9500.0f));
        misPlanes.add(new Plan("P-08", "Empresarial", "Central IP Corporativa", 14000.0f));

        Usuario u1 = new Usuario("amartinez", "1234", Rol.GERENTE);
        Usuario u2 = new Usuario("lgomez", "1234", Rol.SOPORTE_TECNICO);
        
        misEmpleados.add(new Empleado("001-0000000-1", "Ana Martínez", "809-555-0001", "Ensanche Naco", generarIdEmpleado(), "Ventas", 50000.0f, u1, "Activo"));
        misEmpleados.add(new Empleado("001-0000000-2", "Luis Gómez", "809-555-0002", "Los Alcarrizos", generarIdEmpleado(), "Soporte Técnico", 35000.0f, u2, "Activo"));

        Cliente c1 = new Cliente("402-1234567-8", "Juan Pérez", "809-555-1234", "Ensanche Naco, Santo Domingo", generarIdCliente(), "Activo", misPlanes.get(0), "Personal", "N/A");
        c1.setFechaAsignacionPlan(new Date());
        misClientes.add(c1);
        
        Cliente c2 = new Cliente("031-9876543-2", "María Gómez", "829-555-9876", "Los Jardines, Santiago", generarIdCliente(), "Activo", misPlanes.get(3), "Personal", "N/A");
        c2.setFechaAsignacionPlan(new Date());
        misClientes.add(c2);

        Cliente c3 = new Cliente("001-2223334-5", "Tech Solutions SRL", "809-111-2222", "Av. Winston Churchill", generarIdCliente(), "Activo", misPlanes.get(6), "Empresarial", "1-30-12345-6");
        c3.setFechaAsignacionPlan(new Date());
        misClientes.add(c3);
    }

    public String generarIdCliente() {
        String id = "C-00" + contadorClientes;
        contadorClientes++;
        return id;
    }

    public String generarIdEmpleado() {
        String id = "E-" + contadorEmpleados;
        contadorEmpleados++;
        return id;
    }

    public String generarIdContrato() {
        String id = "CTR-" + contadorContratos;
        contadorContratos++;
        return id;
    }

    public String generarIdPago() {
        String id = "PAG-" + contadorPagos;
        contadorPagos++;
        return id;
    }

    public String generarIdPlan() {
        String id = "P-0" + contadorPlanes;
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

    public ArrayList<Servicio> getServicios() {
        return misServicios;
    }

    public void registrarServicio(Servicio s) {
        misServicios.add(s);
    }

    public ArrayList<Contrato> getContratos() {
        return misContratos;
    }

    public void registrarContrato(Contrato c) {
        misContratos.add(c);
    }

    public ArrayList<Pagos> getPagos() {
        return misPagos;
    }

    public void registrarPago(Pagos p) {
        misPagos.add(p);
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

    public static void guardarDatos() {

    }
}