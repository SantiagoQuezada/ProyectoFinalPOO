package Logico;

public class Cliente {
    private String idCliente;
    private String nombre;
    private Plan plan; // Relación con el plan contratado

    public Cliente(String idCliente, String nombre, Plan plan) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.plan = plan;
    }

    public String getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public Plan getPlan() { return plan; }
    
    public void setPlan(Plan plan) { this.plan = plan; }
}