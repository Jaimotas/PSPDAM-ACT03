
public class Pedido {
    private int id;
    private String nombrePlato;

    public Pedido(int id, String nombrePlato) {
        this.id = id;
        this.nombrePlato = nombrePlato;
    }

    public int getId() {
        return id;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }
}
