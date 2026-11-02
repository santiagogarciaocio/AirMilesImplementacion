package aed.tp2;

class Usuario implements Comparable<Usuario> {
    private int ID;
    private double Totales;
    private double Disponibles;
    private double Canjeadas;
    private boolean esParExclusivo;
    private Usuario ultimoTransferidor;
    private int indiceHeap;
    /* guardar la informacion completa de enviadas y recibidas/ de si es trasnferidos y receptor exlusivao
    Entonces cuando estamos recorriendo el ABB para 'paresTransferidoresExlusivos' y vemos que efectivamente es trasnfereridor exclusivo. 
    si es menor a su pareja lo ponemos en la lista
    Si es mayor a su pareja no hacemos nada porque ya lo pusimos
    Se recorre el ABB via iterador desde el menor hasta el mayor*/


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

    public int indiceHeap(){
        return indiceHeap;
    }

    public void setIndiceHeap(int i){
        indiceHeap = i;
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
        /*Hay que agregar que tire error si se quieren canjear mas millas de las que se tiene? 
        Porque es un requiere pero me hace ruido no ponerlo */
        Disponibles = this.Disponibles - millas;
        Canjeadas = this.Canjeadas + millas;
    }    

    /*--------------------------------------------------------------------------------------------------------- */
    /*Realizar transferencia */
    /*--------------------------------------------------------------------------------------------------------- */


    public void transferencia(Usuario us, double millas){
        Disponibles += millas;
        if (ultimoTransferidor == null){
            if (us.ultimoTransferidor() == null || (us.ultimoTransferidor() == this && us.esParExclusivo())){
                ultimoTransferidor = us;
                esParExclusivo = true;
            }
            else {
                ultimoTransferidor = us;
            }
        }
        else if (esParExclusivo() && (ultimoTransferidor() != us)){
            esParExclusivo = false;
            ultimoTransferidor.esParExclusivo = false;            
        }
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

    @Override 
    public String toString(){
        return "{ID Usuario:"+this.ID()+"\nTotales:"+this.totales()+"\n Disponibles:"+this.disponibles()+"\nCanjeadas:"+this.canjeadas()+"\nultimoUsuarioTransferido:"+this.ultimoUsuarioTransferido()+"\nesTransferidorExlusivo:"+this.esTransferidorExlusivo()+"\nultimoUsuarioRecibido:"+this.ultimoUsuarioRecibido+"\nesReceptorExlusivo:"+this.esReceptorExlusivo(); 
    }  

}
