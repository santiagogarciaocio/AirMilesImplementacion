package aed.tp2;

class Usuario implements Comparable<Usuario> {
    private int ID;
    private double Totales;
    private double Disponibles;
    private double Canjeadas;
    private Usuario ultimoUsuarioTransferido;
    private boolean esTransferidorExlusivo;
    private Usuario ultimoUsuarioRecibido;
    private boolean esReceptorExlusivo; 
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
        ultimoUsuarioTransferido = this; /*pongo placeholder el id del propio porque intentando poner null me tira error */
        esTransferidorExlusivo = false;
        ultimoUsuarioRecibido = this;
        esReceptorExlusivo = false;
    }

    public Usuario(int id, boolean referido){
        ID = id;
        Totales = 500;
        Disponibles = 500;      
        Canjeadas = 0;
        ultimoUsuarioTransferido = this; /*pongo placeholder el id del propio porque intentando poner null me tira error */
        esTransferidorExlusivo = false;
        ultimoUsuarioRecibido = this;
        esReceptorExlusivo = false;
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

    public boolean esTransferidorExlusivo(){
        return this.esTransferidorExlusivo;
    }

    public int ultimoUsuarioTransferido(){
        return this.ultimoUsuarioTransferido.ID();
    }

    public boolean esReceptorExlusivo(){
        return this.esReceptorExlusivo;
    }

    public int ultimoUsuarioRecibido(){
        return this.ultimoUsuarioRecibido.ID();
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


    public void transferencia(Usuario receptor, double millas){        
        enviarTransferencia(this, receptor, millas);
        recibirTransferencia(this, receptor, millas);
    }    

    public void enviarTransferencia(Usuario us_transferidor, Usuario us_receptor, double millas){
        us_transferidor.canjearMillas(millas);
        us_transferidor.Totales = us_transferidor.Totales - millas;
        if(us_transferidor.ultimoUsuarioTransferido() == us_transferidor.ID()){
            us_transferidor.ultimoUsuarioTransferido = us_receptor;
            us_transferidor.esTransferidorExlusivo = true;
        }
        else if(us_transferidor.ultimoUsuarioTransferido() != us_receptor.ID()){
            us_transferidor.esTransferidorExlusivo = false;
            desvincularParejaExclusiva(us_transferidor.ultimoUsuarioTransferido,us_transferidor.ultimoUsuarioRecibido);
        }
    }

    
    public void recibirTransferencia(Usuario us_transferidor, Usuario us_receptor, double millas){
        us_receptor.acumularMillas(millas);            
        if(us_receptor.ultimoUsuarioRecibido() == us_receptor.ID()){
            us_receptor.ultimoUsuarioRecibido = us_transferidor;
            us_receptor.esReceptorExlusivo = true;
        }
        else if(us_receptor.ultimoUsuarioRecibido() != us_transferidor.ID()){
            us_receptor.esReceptorExlusivo = false;
            desvincularParejaExclusiva(us_receptor.ultimoUsuarioTransferido, us_receptor.ultimoUsuarioRecibido);
        }
    }    

    public void desvincularParejaExclusiva(Usuario ultimoTransferido, Usuario ultimoRecibido){
        ultimoTransferido.esReceptorExlusivo = false;
        ultimoTransferido.esTransferidorExlusivo = false;
        ultimoRecibido.esReceptorExlusivo = false;
        ultimoRecibido.esTransferidorExlusivo = false;
    }


    /*--------------------------------------------------------------------------------------------------------- */
    /*Chequeo pareja Transferidores Exclusivos */
    /*--------------------------------------------------------------------------------------------------------- */

    public boolean estaEnParejaTransferidoresExclusivos(){
        return (esTransferidorExlusivo() && esReceptorExlusivo()) && (ultimoUsuarioTransferido() == ultimoUsuarioRecibido());
    }

    public int parejaTransferidoraExclusiva(){
        if(estaEnParejaTransferidoresExclusivos()){
            return ultimoUsuarioTransferido();
        }
        else{
            throw new UnsupportedOperationException("No tiene pareja exclusiva");
            /*Aca quizas deberiamos poner para que tire un erro */
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
            return 2.5;
        }
    }

    @Override 
    public int compareTo(Usuario usuario){
        return this.ID - usuario.ID;
    }

    @Override 
    public String toString(){
        return "{ID Usuario:"+this.ID()+"\nTotales:"+this.totales()+"\n Disponibles:"+this.disponibles()+"\nCanjeadas:"+this.canjeadas()+"\nultimoUsuarioTransferido:"+this.ultimoUsuarioTransferido()+"\nesTransferidorExlusivo:"+this.esTransferidorExlusivo()+"\nultimoUsuarioRecibido:"+this.ultimoUsuarioRecibido+"\nesReceptorExlusivo:"+this.esReceptorExlusivo(); 
    }  

}
