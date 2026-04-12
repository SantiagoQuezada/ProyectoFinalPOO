package Logico;

public class Plan {
    private String idPlan;
    private String categoria;
    private String nombre;

    public Plan(String idPlan, String categoria, String nombre) {
        this.idPlan = idPlan;
        this.categoria = categoria;
        this.nombre = nombre;
    }

    public String getIdPlan() { 
        return idPlan; 
    }
    
    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }
    
    public String getCategoria() { 
        return categoria; 
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}