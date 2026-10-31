package aed.tp2;

public class Promocion {
    private String nombre;
    private double factor;
    private boolean tieneTope;
    private double tope;
    private boolean estaActiva;

    public Promocion(String nombre, double factor){
        this.nombre = nombre;
        this.factor = factor;
        this.tieneTope = false;
        this.tope = Double.POSITIVE_INFINITY;
        this.estaActiva = true;
    }

    public Promocion(String nombre, double factor, int tope){
        this.nombre = nombre;
        this.factor = factor;
        this.tieneTope = true;
        this.tope = tope;
        this.estaActiva = true;
    }

    public String nombre(){
        return this.nombre;
    }

    public double factor(){
        return this.factor;
    }

    public boolean tieneTope(){
        return this.tieneTope;
    }

    public double tope(){
        return this.tope;
    }

    public boolean sePasaDelTope(int millas){
        return millas > this.tope;
    }

    public boolean igualAlTope(int millas){
        return millas == this.tope;
    }

    public void restarTope(int millas){
        this.tope = this.tope - millas;
    }

    public void eliminar(){
        this.estaActiva = false;
    }

    public boolean estaActiva(){
        return this.estaActiva;
    }

}

