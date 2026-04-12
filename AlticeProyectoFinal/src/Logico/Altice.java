package Logico;

import java.util.ArrayList;

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
        cargarDatosPrueba();
    }

    public static Altice getInstance() {
        if (instance == null) {
            instance = new Altice();
        }
        return instance;
    }

    private void cargarDatosPrueba() {
        misPlanes.add(new Plan("P-01", "Combinado", "Tripleplay Básico"));
        misPlanes.add(new Plan("P-02", "Combinado", "Tripleplay Premium"));
        misPlanes.add(new Plan("P-03", "Hogar", "Internet Fibra 100Mbps"));
        misPlanes.add(new Plan("P-04", "Hogar", "Internet Fibra 300Mbps"));
        misPlanes.add(new Plan("P-05", "Móvil", "Pospago Básico 15GB"));
        misPlanes.add(new Plan("P-06", "Móvil", "Pospago Ilimitado 5G"));

        Usuario u1 = new Usuario("amartinez", "1234", Rol.GERENTE);
        Usuario u2 = new Usuario("lgomez", "1234", Rol.SOPORTE_TECNICO);
        
        misEmpleados.add(new Empleado("001-0000000-1", "Ana Martínez", "809-555-0001", "Ensanche Naco", generarIdEmpleado(), "Ventas", 50000.0f, u1));
        misEmpleados.add(new Empleado("001-0000000-2", "Luis Gómez", "809-555-0002", "Los Alcarrizos", generarIdEmpleado(), "Soporte", 35000.0f, u2));

        Cliente c1 = new Cliente("402-1234567-8", "Juan Pérez", "809-555-1234", "Ensanche Naco, Santo Domingo", generarIdCliente(), "Activo", misPlanes.get(0));
        misClientes.add(c1);
        
        Cliente c2 = new Cliente("031-9876543-2", "María Gómez", "829-555-9876", "Los Jardines, Santiago", generarIdCliente(), "Activo", misPlanes.get(3));
        misClientes.add(c2);
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

    public ArrayList<Empleado> getEmpleados() {
        return misEmpleados;
    }

    public void registrarEmpleado(Empleado e) {
        misEmpleados.add(e);
    }

    public void eliminarEmpleado(String id) {
        misEmpleados.removeIf(e -> e.getIdEmpleado().equals(id));
    }

    public ArrayList<Cliente> getClientes() {
        return misClientes;
    }

    public void registrarCliente(Cliente c) {
        misClientes.add(c);
    }

    public void eliminarCliente(String id) {
        misClientes.removeIf(c -> c.getIdCliente().equals(id));
    }

    public ArrayList<Plan> getPlanes() {
        return misPlanes;
    }

    public void registrarPlan(Plan p) {
        misPlanes.add(p);
    }

    public void eliminarPlan(String idPlan) {
        misPlanes.removeIf(p -> p.getIdPlan().equals(idPlan));
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
            if (p.getCategoria().equals(categoria)) {
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