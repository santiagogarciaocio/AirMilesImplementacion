package aed.tp2;

class Usuario implements Comparable<Usuario> {
    private int ID;
    private double Totales;
    private double Disponibles;
    private double Canjeadas;
    private boolean esParExclusivo;
    private Usuario ultimoTransferidor;
    
    /*--------------------------------------------------------------------------------------------------------- */
    /* Creadores */
    /*--------------------------------------------------------------------------------------------------------- */
    public Usuario(int id){
        ID = id;
        Totales = 0;
        Disponibles = 0;      
        Canjeadas = 0;
        esParExclusivo = false;
    }

    public Usuario(int id, boolean referido){
        ID = id;
        Totales = 500;
        Disponibles = 500;      
        Canjeadas = 0;
        esParExclusivo = false;
    }

    /*--------------------------------------------------------------------------------------------------------- */
    /* Averiguar Atributos */
    /*--------------------------------------------------------------------------------------------------------- */

    public int ID(){
        return this.ID;
    }
    
    public double totales(){
        return this.Totales;
    }

    public double disponibles(){
        return this.Disponibles;    
    }

    public double canjeadas(){
        return this.Canjeadas;
    }

    public boolean esParExclusivo(){
        return this.esParExclusivo;
    }

    public Usuario ultimoTransferidor(){
        return this.ultimoTransferidor;
    }

    /*--------------------------------------------------------------------------------------------------------- */
    /*Realizar Acciones */
    /*--------------------------------------------------------------------------------------------------------- */

    public void acumularMillas(double millas){
        Totales = this.Totales + millas;
        Disponibles = this.Disponibles + millas;
    }

    public void canjearMillas(double millas){
        Disponibles = this.Disponibles - millas;
        Canjeadas = this.Canjeadas + millas;
    }    

    /*--------------------------------------------------------------------------------------------------------- */
    /*Realizar transferencia */
    /*--------------------------------------------------------------------------------------------------------- */


    public void recibirTransferencia(Usuario dador, double millas){
        Disponibles += millas;
        dador.Disponibles -= millas;
        dador.Canjeadas += millas;
        if (ultimoTransferidor == null && dador.ultimoTransferidor == null){
            esParExclusivo = true;
            dador.esParExclusivo = true;
        }
        else {
            if (ultimoTransferidor != dador && this.esParExclusivo()) {
                esParExclusivo = false;
                ultimoTransferidor.esParExclusivo = false;
            }
            if (dador.ultimoTransferidor != this && dador.esParExclusivo()) {
                dador.esParExclusivo = false;
                dador.ultimoTransferidor.esParExclusivo = false;
            }
        }
        ultimoTransferidor = dador;
        dador.ultimoTransferidor = this;
    }

    /*Consultar categoria */

    public String categoriaStrg(){
        if(this.Totales < 10000){
            return "Bronce";
        }else if (this.Totales < 50000){
            return "Plata";
        }else if(this.Totales < 1000000){
            return "Oro";
        }else{
            return "Platino";
        }
    }

    public double categoria(){
        if(this.Totales < 10000){
            return 1.0;
        }else if (this.Totales < 50000){
            return 1.5;
        }else if(this.Totales < 1000000){
            return 2.0;
        }else{
            return 0;
        }
    }

    @Override 
    public int compareTo(Usuario usuario){
        double diferencia = this.Disponibles - usuario.Disponibles;
        if (diferencia > 0){
            return 1;            
        }
        else if (diferencia < 0){
            return -1;
        }
        else {
            return this.ID - usuario.ID;
        }
    } 
}
