package aed.tp2;

class TrieMalo {
    Nodo root;
    int tamaño;

    public class Nodo {
    char letra;
    Promocion promocion;
    Nodo hermano;
    Nodo hijo;

    public Nodo(){
        this.letra = '$';
        this.promocion = null;
        this.hermano = null;
        this.hijo = null;
    }

    public Nodo(char letra){
        this.letra = letra;
        this.promocion = null;
        this.hermano = null;
        this.hijo = null;
    }

    public Nodo(char letra, Promocion promo){
        this.letra = letra;
        this.promocion = promo;
        this.hermano = null;
        this.hijo = null;
    }

    }

    public TrieMalo(){
        this.root = new Nodo();
        this.tamaño = 0;
        agregar("No promo", 1);
    }

    public int tamaño(){
        return this.tamaño;
    }

    public boolean pertenece(String promo){
        if(tamaño() == 0){return false;}
        Nodo iterador = root.hijo;
        int i = 0;
        while(iterador.hijo == null || iterador.hermano == null){
            if(iterador.letra == '$'){
                if(iterador.promocion.nombre() == promo){
                break;}
            }
            posicionamientoHorizontal(iterador, promo, i);                           
            if(iterador.letra != promo.charAt(i)){
                break;
            }
            else{
                iterador = iterador.hijo;
                i ++;
            }
        }  
        if(iterador.letra =='$'){
            return iterador.promocion.nombre() == promo;
        }else{
            return false;
        }
    }

    private void posicionamientoHorizontal(Nodo iterador,String promo, int i){
        while((iterador.letra > promo.charAt(i)) || (iterador.hermano != null)){
            iterador = iterador.hermano;
        }
    }

    public void agregar(String promo, double factor){
        if(tamaño() == 0){
            crearRama(root, 0, promo, factor);
            this.tamaño ++;
        }
        else{
        Nodo iterador = root.hijo;
        int i = 0;
        while(iterador.hijo == null || iterador.hermano == null){
            if(iterador.letra == '$'){
                if(iterador.promocion.nombre() == promo){
                break;}
            }
            posicionamientoHorizontal(iterador, promo, i);                           
            if(iterador.letra != promo.charAt(i)){
                break;
            }
            else{
                iterador = iterador.hijo;
                i ++;
            }
        }
        if(iterador.letra == '$'){
            if(iterador.promocion.nombre() == promo){
                throw new UnsupportedOperationException("La promocion ya existe");
        }
        else{
            if(iterador.letra == promo.charAt(i)){
                crearRama(iterador,i,promo,factor); 
                tamaño ++;               
            }
            else{
                iterador.hermano = new Nodo(promo.charAt(i));
                crearRama(iterador.hermano, i++, promo, factor);
                tamaño ++;
            }}
        }}
    }    

    private void crearRama(Nodo nodoInicial, int desde, String promo, double factor){
        for(;desde < promo.length(); desde++){
            nodoInicial.hijo = new Nodo(promo.charAt(desde));
            nodoInicial = nodoInicial.hijo;
        }
        nodoInicial.hijo = new Nodo('$', new Promocion(promo,factor));
    }

    public void agregar(String promo, double factor, int tope){
        if(tamaño() == 0){
            crearRama(root, 0, promo, factor, tope);
            this.tamaño ++;
        }
        else{
        Nodo iterador = root.hijo;
        int i = 0;
        while(iterador.hijo == null || iterador.hermano == null){
            if(iterador.letra == '$'){
                if(iterador.promocion.nombre() == promo){
                break;}
            }
            posicionamientoHorizontal(iterador, promo, i);                           
            if(iterador.letra != promo.charAt(i)){
                break;
            }
            else{
                iterador = iterador.hijo;
                i ++;
            }
        }
        if(iterador.letra == '$'){
            if(iterador.promocion.nombre() == promo){
                throw new UnsupportedOperationException("La promocion ya existe");
        }
        else{
            if(iterador.letra == promo.charAt(i)){
                crearRama(iterador,i,promo,factor,tope); 
                tamaño ++;               
            }
            else{
                iterador.hermano = new Nodo(promo.charAt(i));
                crearRama(iterador.hermano, i++, promo, factor,tope);
                tamaño ++;
            }}
        }}
    }

    private void crearRama(Nodo nodoInicial, int desde, String promo, double factor, int tope){
        for(; desde < promo.length(); desde++){
            nodoInicial.hijo = new Nodo(promo.charAt(desde));
            nodoInicial = nodoInicial.hijo;
        }
        nodoInicial.hijo = new Nodo('$', new Promocion(promo,factor,tope));
    }

    public void sacar(String promo){
        if(tamaño() == 0){
            throw new UnsupportedOperationException("La promocion no existe");
        }
        Nodo iterador = root.hijo;
        int i = 0;
        while((iterador.promocion.nombre() != promo) && (iterador.hijo == null && iterador.hermano == null)){
            posicionamientoHorizontal(iterador, promo, i);
            if(iterador.promocion.nombre() == promo){
                continue;
            }
            if(iterador.hijo == null){
                continue;
            }else{
                iterador = iterador.hijo;
                i ++;
            }
        }
        if(iterador.promocion.nombre() != promo){
            throw new UnsupportedOperationException("La promocion no existe");
        }else{
            iterador.promocion = null;
        }

    }
  
}
