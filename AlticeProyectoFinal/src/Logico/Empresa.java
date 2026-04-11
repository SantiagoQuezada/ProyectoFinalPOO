package Logico;

import java.util.ArrayList;

public class Empresa {
    private static Empresa instance; // Instancia única (Singleton)
    private ArrayList<Empleado> misEmpleados;
    private ArrayList<Cliente> misClientes;
    private ArrayList<Plan> misPlanes;

    // Constructor privado para el patrón Singleton
    private Empresa() {
        misEmpleados = new ArrayList<>();
        misClientes = new ArrayList<>();
        misPlanes = new ArrayList<>();
        cargarDatosPrueba(); // Cargar algunos datos para que no esté vacío al iniciar
    }

    // Método para obtener la instancia global
    public static Empresa getInstance() {
        if (instance == null) {
            instance = new Empresa();
        }
        return instance;
    }

    private void cargarDatosPrueba() {
        // Planes
        misPlanes.add(new Plan("P-01", "Combinado", "Tripleplay Básico (TV + Net 100MB + Voz)"));
        misPlanes.add(new Plan("P-02", "Combinado", "Tripleplay Premium (TV Max + Net 300MB + Voz)"));
        misPlanes.add(new Plan("P-03", "Hogar", "Internet Fibra 100Mbps"));
        misPlanes.add(new Plan("P-04", "Hogar", "Internet Fibra 300Mbps"));
        misPlanes.add(new Plan("P-05", "Móvil", "Pospago Básico 15GB"));
        misPlanes.add(new Plan("P-06", "Móvil", "Pospago Ilimitado 5G"));

        // Empleados y Clientes base
        misEmpleados.add(new Empleado("E-1001", "Ana Martínez", "Gerente de Ventas"));
        misEmpleados.add(new Empleado("E-1002", "Luis Gómez", "Soporte Técnico"));
        
        misClientes.add(new Cliente("C-0010", "Juan Pérez", misPlanes.get(0)));
        misClientes.add(new Cliente("C-0011", "María Gómez", misPlanes.get(3)));
    }

    // --- MÉTODOS CRUD EMPLEADOS ---
    public ArrayList<Empleado> getEmpleados() { return misEmpleados; }
    public void registrarEmpleado(Empleado e) { misEmpleados.add(e); }
    public void eliminarEmpleado(String id) {
        misEmpleados.removeIf(e -> e.getIdEmpleado().equals(id));
    }

    // --- MÉTODOS CRUD CLIENTES ---
    public ArrayList<Cliente> getClientes() { return misClientes; }
    public void registrarCliente(Cliente c) { misClientes.add(c); }
    public void eliminarCliente(String id) {
        misClientes.removeIf(c -> c.getIdCliente().equals(id));
    }

    // --- MÉTODOS PARA LOS COMBOBOX DE LA VENTANA PLANES ---
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
}