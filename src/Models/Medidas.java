package Models;

public class Medidas {

    private int idMedidas;
    private String nombre;
    private String nombreConto;
    private String estado;

    public Medidas() {
    }

    public Medidas(int idMedidas, String nombre, String nombreConto, String estado) {
        this.idMedidas = idMedidas;
        this.nombre = nombre;
        this.nombreConto = nombreConto;
        this.estado = estado;
    }

    public int getIdMedidas() {
        return idMedidas;
    }

    public void setIdMedidas(int idMedidas) {
        this.idMedidas = idMedidas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreConto() {
        return nombreConto;
    }

    public void setNombreConto(String nombreConto) {
        this.nombreConto = nombreConto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
