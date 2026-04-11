package Logico;

public class Plan {
    private String idPlan;
    private String categoria; // Combinado, Hogar, Móvil
    private String nombre;

    public Plan(String idPlan, String categoria, String nombre) {
        this.idPlan = idPlan;
        this.categoria = categoria;
        this.nombre = nombre;
    }

    public String getIdPlan() { return idPlan; }
    public String getCategoria() { return categoria; }
    public String getNombre() { return nombre; }
}