package Logico;

public class Plan {
    private String idPlan;
    private String categoria;
    private String nombre;
    private float precio;
    private String estado;

    public Plan(String idPlan, String categoria, String nombre, float precio) {
        this.idPlan = idPlan;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = "Activo";
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}